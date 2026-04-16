package com.am.ipl.analytics.service;

import com.am.ipl.analytics.bean.ApiResponse;
import com.am.ipl.analytics.bean.MatchData;
import com.am.ipl.analytics.entity.CricketMatch;
import com.am.ipl.analytics.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchSyncService {

    private final MatchRepository matchRepository;
    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${cricket.api.key}")
    private String apiKey;

    public int syncMatches() {
        System.out.println("Manual Sync triggered via API... " + LocalDateTime.now());

        String url = "https://api.cricapi.com/v1/currentMatches?apikey=" + apiKey;
        int updateCount = 0;

        try {
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

            if (response != null && "success".equals(response.getStatus()) && response.getData() != null) {
                for (MatchData data : response.getData()) {
                    upsertMatch(data);
                    updateCount++;
                }
                List<CricketMatch> allMatches = matchRepository.findAll();
                messagingTemplate.convertAndSend("/topic/matches", allMatches);
                System.out.println("Broadcasted " + allMatches.size() + " matches via WebSocket.");
            }
        } catch (Exception e) {
            System.err.println("API Sync Failed: " + e.getMessage());
            throw new RuntimeException("Failed to sync with cricket API");
        }
        return updateCount;
    }

    private void upsertMatch(MatchData data) {
        CricketMatch match = matchRepository.findById(data.getId())
                .orElse(new CricketMatch());

        match.setId(data.getId());
        match.setName(data.getName());
        match.setMatchType(data.getMatchType());
        match.setStatus(data.getStatus());
        match.setVenue(data.getVenue());
        match.setDate(data.getDate());
        match.setSeriesId(data.getSeries_id());
        match.setMatchStarted(data.isMatchStarted());
        match.setMatchEnded(data.isMatchEnded());
        match.setLastUpdated(LocalDateTime.now());

        // Join teams list into a single string
        if (data.getTeams() != null) {
            match.setTeams(String.join(" vs ", data.getTeams()));
        }

        // Format Score list into a readable string: "Inning1: 146/10 (20.0), Inning2: 149/5 (15.1)"
        if (data.getScore() != null && !data.getScore().isEmpty()) {
            String formattedScore = data.getScore().stream()
                    .map(s -> s.getInning() + ": " + s.getR() + "/" + s.getW() + " (" + s.getO() + ")")
                    .collect(Collectors.joining(" | "));
            match.setScoreSummary(formattedScore);
        } else {
            match.setScoreSummary("No score available yet");
        }

        matchRepository.save(match);
    }
}

package com.am.ipl.analytics.controller;

import com.am.ipl.analytics.service.MatchSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchSyncService matchSyncService;

    @PostMapping("/sync")
    public ResponseEntity<String> triggerSync() {
        int count = matchSyncService.syncMatches();
        return ResponseEntity.ok("Sync successful! Updated " + count + " matches.");
    }
}

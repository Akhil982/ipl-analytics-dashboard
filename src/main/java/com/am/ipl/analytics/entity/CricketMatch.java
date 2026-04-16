package com.am.ipl.analytics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CricketMatch {
    @Id
    private String id;
    private String name;
    private String matchType;
    private String status;
    private String venue;
    private String date;
    private String seriesId;
    private String teams; // Stored as "Team A vs Team B"
    private String scoreSummary; // Formatted inning scores
    private boolean matchStarted;
    private boolean matchEnded;
    private LocalDateTime lastUpdated;
}

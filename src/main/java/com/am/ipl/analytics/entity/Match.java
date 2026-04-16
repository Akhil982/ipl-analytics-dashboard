package com.am.ipl.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ipl_matches")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matchId; // External API ID
    private String teamHome;
    private String teamAway;
    private String venue;
    private String liveScore;
    private String status; // LIVE, UPCOMING, COMPLETED
    private LocalDateTime lastUpdated;
}

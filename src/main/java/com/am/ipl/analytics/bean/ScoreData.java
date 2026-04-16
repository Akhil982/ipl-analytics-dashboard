package com.am.ipl.analytics.bean;

import lombok.Data;

@Data
public class ScoreData {
    private int r; // Runs
    private int w; // Wickets
    private double o; // Overs
    private String inning;
}

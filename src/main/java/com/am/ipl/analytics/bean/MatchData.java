package com.am.ipl.analytics.bean;

import lombok.Data;

import java.util.List;

@Data
public class MatchData {
    private String id;
    private String name;
    private String matchType;
    private String status;
    private String venue;
    private String date;
    private String series_id;
    private List<String> teams;
    private List<ScoreData> score;
    private boolean matchStarted;
    private boolean matchEnded;
}

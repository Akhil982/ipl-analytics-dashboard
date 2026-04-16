package com.am.ipl.analytics.bean;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private String status;
    private List<MatchData> data;
}

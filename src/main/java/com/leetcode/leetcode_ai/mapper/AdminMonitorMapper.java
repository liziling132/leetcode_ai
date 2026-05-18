package com.leetcode.leetcode_ai.mapper;

public interface AdminMonitorMapper {
    long countUsers();

    long countProblems();

    long countSubmissions();

    long countPendingSubmissions();

    long countFailedSubmissions24h();

    long countRecommendations24h();
}

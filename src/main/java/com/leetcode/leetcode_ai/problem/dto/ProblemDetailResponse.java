package com.leetcode.leetcode_ai.problem.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProblemDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String inputDesc;
    private String outputDesc;
    private String difficulty;
    private List<String> knowledgePoints;
    private List<String> supportLanguages;
    private List<String> tags;
}

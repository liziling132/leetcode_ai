package com.leetcode.leetcode_ai.problem.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProblemListItemResponse {
    private Long id;
    private String title;
    private String difficulty;
    private List<String> supportLanguages;
    private List<String> tags;
}

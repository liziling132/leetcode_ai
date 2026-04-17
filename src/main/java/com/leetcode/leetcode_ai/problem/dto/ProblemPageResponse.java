package com.leetcode.leetcode_ai.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProblemPageResponse {
    private long total;
    private int page;
    private int size;
    private List<ProblemListItemResponse> list;
}

package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProblemListItemResponseVo {
    private Long id;
    private String title;
    private String difficulty;
    private List<String> supportLanguages;
    private List<String> tags;
}

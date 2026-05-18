package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.util.List;

@Data
public class AdminProblemDetailVo {
    private Long id;
    private String title;
    private String content;
    private String inputDesc;
    private String outputDesc;
    private String difficulty;
    private List<String> knowledgePoints;
    private List<String> supportLanguages;
    private Integer status;
    private List<Long> tagIds;
    private List<String> tagNames;
    private List<AdminProblemTestcaseVo> testcases;
}

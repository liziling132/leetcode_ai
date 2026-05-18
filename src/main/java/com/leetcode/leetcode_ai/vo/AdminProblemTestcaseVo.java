package com.leetcode.leetcode_ai.vo;

import lombok.Data;

@Data
public class AdminProblemTestcaseVo {
    private Long id;
    private String caseType;
    private String inputData;
    private String outputData;
    private Integer score;
    private Integer sortOrder;
    private Integer isActive;
}

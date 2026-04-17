package com.leetcode.leetcode_ai.testcase.entity;

import lombok.Data;

@Data
public class Testcase {
    private Long id;
    private Long problemId;
    private String caseType;
    private String inputData;
    private String outputData;
    private Integer score;
    private Integer sortOrder;
    private Integer isActive;
}

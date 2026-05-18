package com.leetcode.leetcode_ai.entity;

import lombok.Data;

@Data
public class TestcaseEntity {
    private Long id;
    private Long problemId;
    private String caseType;
    private String inputData;
    private String outputData;
    private Integer score;
    private Integer sortOrder;
    private Integer isActive;
}

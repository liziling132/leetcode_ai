package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiCodeExplainVo {
    private Long submissionId;
    private String explanation;
    private String aiSource;
    private String aiModel;
}

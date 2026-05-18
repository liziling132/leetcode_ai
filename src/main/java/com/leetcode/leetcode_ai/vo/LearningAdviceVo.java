package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LearningAdviceVo {
    private String advice;
    private String aiSource;
    private String aiModel;
}

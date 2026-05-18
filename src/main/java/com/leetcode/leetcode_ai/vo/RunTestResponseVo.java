package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RunTestResponseVo {
    private String status;
    private String output;
    private String errorMessage;
    private Integer runTimeMs;
}

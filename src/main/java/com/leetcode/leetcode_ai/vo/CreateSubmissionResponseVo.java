package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSubmissionResponseVo {
    private Long submissionId;
    private String judgeStatus;
}

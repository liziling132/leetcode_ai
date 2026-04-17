package com.leetcode.leetcode_ai.submission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSubmissionResponse {
    private Long submissionId;
    private String judgeStatus;
}

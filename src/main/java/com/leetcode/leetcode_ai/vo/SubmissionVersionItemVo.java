package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionVersionItemVo {
    private Long submissionId;
    private String language;
    private String judgeStatus;
    private String codeContent;
    private LocalDateTime submittedAt;
}

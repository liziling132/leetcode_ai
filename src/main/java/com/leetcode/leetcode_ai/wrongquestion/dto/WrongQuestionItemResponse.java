package com.leetcode.leetcode_ai.wrongquestion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WrongQuestionItemResponse {
    private Long id;
    private Long problemId;
    private String problemTitle;
    private String difficulty;
    private Integer wrongCount;
    private String lastWrongType;
    private Long lastSubmissionId;
    private String aiSummary;
    private Integer status;
    private LocalDateTime lastWrongAt;
}

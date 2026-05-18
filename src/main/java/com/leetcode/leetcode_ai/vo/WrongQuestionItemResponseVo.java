package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WrongQuestionItemResponseVo {
    private Long id;
    private Long problemId;
    private String problemTitle;
    private String difficulty;
    private Integer wrongCount;
    private String lastWrongType;
    private Long lastSubmissionId;
    private String aiSummary;
    private String aiSource;
    private Integer status;
    private LocalDateTime lastWrongAt;
}

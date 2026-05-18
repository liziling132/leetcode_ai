package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RecommendationRecordItemVo {
    private Long id;
    private String triggerSource;
    private Long triggerRefId;
    private String resourceType;
    private String resourceId;
    private Long problemId;
    private String problemTitle;
    private String difficulty;
    private String reasonText;
    private String aiSource;
    private String aiModel;
    private BigDecimal score;
    private LocalDateTime recommendedAt;
}

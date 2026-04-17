package com.leetcode.leetcode_ai.recommendation.mapper.row;

import lombok.Data;

@Data
public class WrongSeedRow {
    private Long problemId;
    private String difficulty;
    private String lastWrongType;
    private Integer wrongCount;
}

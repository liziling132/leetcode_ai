package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendationResponseVo {
    private int size;
    private List<RecommendationItemResponseVo> list;
}

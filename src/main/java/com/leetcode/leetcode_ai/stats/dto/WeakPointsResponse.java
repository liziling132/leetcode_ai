package com.leetcode.leetcode_ai.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeakPointsResponse {
    private int size;
    private List<WeakPointItem> list;
}

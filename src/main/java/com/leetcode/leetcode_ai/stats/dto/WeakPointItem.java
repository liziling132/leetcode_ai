package com.leetcode.leetcode_ai.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeakPointItem {
    private String tagName;
    private long wrongCount;
}

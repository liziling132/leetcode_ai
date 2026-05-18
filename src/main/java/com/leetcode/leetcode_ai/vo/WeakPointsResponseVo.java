package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeakPointsResponseVo {
    private int size;
    private List<WeakPointItemVo> list;
}

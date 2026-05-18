package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminProblemPageVo {
    private long total;
    private int page;
    private int size;
    private List<AdminProblemListItemVo> list;
}

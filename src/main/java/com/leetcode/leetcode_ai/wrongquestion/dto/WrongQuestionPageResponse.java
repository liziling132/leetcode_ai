package com.leetcode.leetcode_ai.wrongquestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WrongQuestionPageResponse {
    private long total;
    private int page;
    private int size;
    private List<WrongQuestionItemResponse> list;
}

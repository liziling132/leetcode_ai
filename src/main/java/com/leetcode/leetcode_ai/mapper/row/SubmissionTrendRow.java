package com.leetcode.leetcode_ai.mapper.row;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubmissionTrendRow {
    private LocalDate day;
    private Long submissions;
}

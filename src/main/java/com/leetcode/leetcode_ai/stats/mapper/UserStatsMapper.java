package com.leetcode.leetcode_ai.stats.mapper;

import com.leetcode.leetcode_ai.stats.mapper.row.SubmissionTrendRow;
import com.leetcode.leetcode_ai.stats.mapper.row.WeakTagRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserStatsMapper {

    long countSubmissions(@Param("userId") Long userId);

    long countAcceptedSubmissions(@Param("userId") Long userId);

    List<SubmissionTrendRow> submissionTrendLastDays(@Param("userId") Long userId,
                                                     @Param("days") int days);

    List<WeakTagRow> topWeakTags(@Param("userId") Long userId,
                                 @Param("limit") int limit);
}

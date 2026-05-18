package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.vo.DailySubmissionPointVo;
import com.leetcode.leetcode_ai.vo.UserStatsResponseVo;
import com.leetcode.leetcode_ai.vo.WeakPointItemVo;
import com.leetcode.leetcode_ai.vo.WeakPointsResponseVo;
import com.leetcode.leetcode_ai.mapper.UserStatsMapper;
import com.leetcode.leetcode_ai.mapper.row.SubmissionTrendRow;
import com.leetcode.leetcode_ai.mapper.row.WeakTagRow;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserStatsServiceImpl implements UserStatsService {

    private static final int TREND_DAYS = 7;
    private static final int MAX_WEAK_TAG_SIZE = 20;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final UserStatsMapper userStatsMapper;

    @Override
    public UserStatsResponseVo userStats() {
        Long userId = currentUserId();
        long total = userStatsMapper.countSubmissions(userId);
        long accepted = userStatsMapper.countAcceptedSubmissions(userId);
        double rate = total == 0 ? 0D :
                BigDecimal.valueOf((double) accepted * 100 / total).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<SubmissionTrendRow> trendRows = userStatsMapper.submissionTrendLastDays(userId, TREND_DAYS - 1);
        Map<LocalDate, Long> trendMap = new HashMap<>();
        for (SubmissionTrendRow row : trendRows) {
            trendMap.put(row.getDay(), row.getSubmissions() == null ? 0L : row.getSubmissions());
        }

        List<DailySubmissionPointVo> trend = new ArrayList<>(TREND_DAYS);
        LocalDate today = LocalDate.now();
        for (int i = TREND_DAYS - 1; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            long count = trendMap.getOrDefault(day, 0L);
            trend.add(new DailySubmissionPointVo(day.format(DATE_FORMATTER), count));
        }

        return new UserStatsResponseVo(total, accepted, rate, trend);
    }

    @Override
    public WeakPointsResponseVo weakPoints(int size) {
        if (size < 1 || size > MAX_WEAK_TAG_SIZE) {
            throw new BizException(400, "Invalid size");
        }
        Long userId = currentUserId();
        List<WeakTagRow> rows = userStatsMapper.topWeakTags(userId, size);
        List<WeakPointItemVo> list = new ArrayList<>(rows.size());
        for (WeakTagRow row : rows) {
            list.add(new WeakPointItemVo(row.getTagName(), row.getWrongCount() == null ? 0L : row.getWrongCount()));
        }
        return new WeakPointsResponseVo(list.size(), list);
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}

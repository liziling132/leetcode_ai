package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.vo.DailySubmissionPointVo;
import com.leetcode.leetcode_ai.vo.UserStatsResponseVo;
import com.leetcode.leetcode_ai.vo.WeakPointItemVo;
import com.leetcode.leetcode_ai.vo.WeakPointsResponseVo;
import com.leetcode.leetcode_ai.vo.LearningAdviceVo;
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
    private static final int ADVICE_WEAK_TAG_SIZE = 3;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final UserStatsMapper userStatsMapper;
    private final AiTextService aiTextService;

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

    @Override
    public LearningAdviceVo learningAdvice() {
        Long userId = currentUserId();
        long total = userStatsMapper.countSubmissions(userId);
        long accepted = userStatsMapper.countAcceptedSubmissions(userId);
        long wrongTotal = userStatsMapper.countWrongQuestions(userId);
        List<WeakTagRow> weakTags = userStatsMapper.topWeakTags(userId, ADVICE_WEAK_TAG_SIZE);
        String weakTagText = weakTags.isEmpty()
                ? "none"
                : weakTags.stream().map(WeakTagRow::getTagName).toList().toString();

        String context = "totalSubmissions=" + total
                + ", accepted=" + accepted
                + ", wrongCount=" + wrongTotal
                + ", weakTags=" + weakTagText;
        AiTextResult aiResult = aiTextService.learningAdvice(context);

        String advice;
        String source;
        String model;
        if (aiResult != null && aiResult.content() != null && !aiResult.content().isBlank()) {
            advice = aiResult.content();
            source = aiResult.source();
            model = aiResult.model();
        } else {
            advice = buildRuleAdvice(total, accepted, wrongTotal, weakTags);
            source = "RULE";
            model = null;
        }

        return new LearningAdviceVo(advice, source, model);
    }

    private String buildRuleAdvice(long total, long accepted, long wrongTotal, List<WeakTagRow> weakTags) {
        double rate = total == 0 ? 0D : (double) accepted * 100D / total;
        String focus = weakTags.isEmpty() ? "基础语法与边界判断" : weakTags.get(0).getTagName();
        return "当前主要问题：通过率约" + BigDecimal.valueOf(rate).setScale(1, RoundingMode.HALF_UP) + "%，累计错题次数" + wrongTotal + "。\n"
                + "接下来3天练习重点：" + focus + "，每天完成2-3道同类题并复盘。\n"
                + "今日建议行动：先做1道简单题热身，再做2道" + focus + "相关题，最后总结错因。";
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}

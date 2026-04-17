package com.leetcode.leetcode_ai.wrongquestion.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.wrongquestion.dto.WrongQuestionItemResponse;
import com.leetcode.leetcode_ai.wrongquestion.dto.WrongQuestionPageResponse;
import com.leetcode.leetcode_ai.wrongquestion.mapper.WrongQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WrongQuestionServiceImpl implements WrongQuestionService {

    private static final int MAX_PAGE_SIZE = 100;
    private final WrongQuestionMapper wrongQuestionMapper;

    @Override
    public WrongQuestionPageResponse list(int page, int size) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        Long userId = currentUserId();
        long total = wrongQuestionMapper.countByUserId(userId);
        if (total == 0) {
            return new WrongQuestionPageResponse(0, page, size, Collections.emptyList());
        }
        int offset = (page - 1) * size;
        List<WrongQuestionItemResponse> list = wrongQuestionMapper.findPageByUserId(userId, offset, size);
        return new WrongQuestionPageResponse(total, page, size, list);
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}

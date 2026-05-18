package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.dto.AdminProblemUpsertRequestDto;
import com.leetcode.leetcode_ai.dto.AdminTestcaseBatchUpsertRequestDto;
import com.leetcode.leetcode_ai.service.AdminProblemService;
import com.leetcode.leetcode_ai.vo.AdminProblemDetailVo;
import com.leetcode.leetcode_ai.vo.AdminProblemIdVo;
import com.leetcode.leetcode_ai.vo.AdminProblemPageVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/problems")
@RequiredArgsConstructor
public class AdminProblemController {

    private final AdminProblemService adminProblemService;

    @GetMapping
    public ApiResponse<AdminProblemPageVo> list(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "20") int size,
                                                @RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String difficulty,
                                                @RequestParam(required = false) Integer status,
                                                @RequestParam(required = false) Long tagId) {
        return ApiResponse.success(adminProblemService.list(page, size, keyword, difficulty, status, tagId));
    }

    @GetMapping("/{id}")
    public ApiResponse<AdminProblemDetailVo> detail(@PathVariable("id") Long id) {
        return ApiResponse.success(adminProblemService.detail(id));
    }

    @PostMapping
    public ApiResponse<AdminProblemIdVo> create(@Valid @RequestBody AdminProblemUpsertRequestDto request) {
        Long problemId = adminProblemService.create(request);
        return ApiResponse.success(new AdminProblemIdVo(problemId));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable("id") Long id,
                                    @Valid @RequestBody AdminProblemUpsertRequestDto request) {
        adminProblemService.update(id, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Long id) {
        adminProblemService.delete(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}/testcases")
    public ApiResponse<Void> replaceTestcases(@PathVariable("id") Long id,
                                              @Valid @RequestBody AdminTestcaseBatchUpsertRequestDto request) {
        adminProblemService.replaceTestcases(id, request);
        return ApiResponse.success(null);
    }
}

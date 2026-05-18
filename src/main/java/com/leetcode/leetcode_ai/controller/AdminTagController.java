package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.dto.AdminTagUpsertRequestDto;
import com.leetcode.leetcode_ai.service.AdminTagService;
import com.leetcode.leetcode_ai.vo.AdminTagIdVo;
import com.leetcode.leetcode_ai.vo.AdminTagPageVo;
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
@RequestMapping("/api/admin/tags")
@RequiredArgsConstructor
public class AdminTagController {

    private final AdminTagService adminTagService;

    @GetMapping
    public ApiResponse<AdminTagPageVo> list(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "20") int size,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String tagType) {
        return ApiResponse.success(adminTagService.list(page, size, keyword, tagType));
    }

    @PostMapping
    public ApiResponse<AdminTagIdVo> create(@Valid @RequestBody AdminTagUpsertRequestDto request) {
        Long tagId = adminTagService.create(request);
        return ApiResponse.success(new AdminTagIdVo(tagId));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable("id") Long id,
                                    @Valid @RequestBody AdminTagUpsertRequestDto request) {
        adminTagService.update(id, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Long id,
                                    @RequestParam(defaultValue = "false") boolean force) {
        adminTagService.delete(id, force);
        return ApiResponse.success(null);
    }
}

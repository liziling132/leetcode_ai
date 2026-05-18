package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.dto.AdminResourceUpsertRequestDto;
import com.leetcode.leetcode_ai.service.AdminResourceService;
import com.leetcode.leetcode_ai.vo.AdminResourceIdVo;
import com.leetcode.leetcode_ai.vo.AdminResourceItemVo;
import com.leetcode.leetcode_ai.vo.AdminResourcePageVo;
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
@RequestMapping("/api/admin/resources")
@RequiredArgsConstructor
public class AdminResourceController {

    private final AdminResourceService adminResourceService;

    @GetMapping
    public ApiResponse<AdminResourcePageVo> list(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "20") int size,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String resourceType,
                                                 @RequestParam(required = false) Integer status) {
        return ApiResponse.success(adminResourceService.list(page, size, keyword, resourceType, status));
    }

    @GetMapping("/{id}")
    public ApiResponse<AdminResourceItemVo> detail(@PathVariable("id") Long id) {
        return ApiResponse.success(adminResourceService.detail(id));
    }

    @PostMapping
    public ApiResponse<AdminResourceIdVo> create(@Valid @RequestBody AdminResourceUpsertRequestDto request) {
        return ApiResponse.success(new AdminResourceIdVo(adminResourceService.create(request)));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable("id") Long id,
                                    @Valid @RequestBody AdminResourceUpsertRequestDto request) {
        adminResourceService.update(id, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Long id) {
        adminResourceService.delete(id);
        return ApiResponse.success(null);
    }
}

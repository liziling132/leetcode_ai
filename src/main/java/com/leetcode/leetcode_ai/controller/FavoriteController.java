package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.service.FavoriteService;
import com.leetcode.leetcode_ai.vo.FavoritePageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/api/problems/{id}/favorite")
    public ApiResponse<Void> favorite(@PathVariable("id") Long problemId) {
        favoriteService.favorite(problemId);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/api/problems/{id}/favorite")
    public ApiResponse<Void> unfavorite(@PathVariable("id") Long problemId) {
        favoriteService.unfavorite(problemId);
        return ApiResponse.success(null);
    }

    @GetMapping("/api/users/me/favorites")
    public ApiResponse<FavoritePageVo> myFavorites(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.success(favoriteService.myFavorites(page, size));
    }
}

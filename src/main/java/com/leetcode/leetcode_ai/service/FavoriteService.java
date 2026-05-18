package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.FavoritePageVo;

public interface FavoriteService {

    void favorite(Long problemId);

    void unfavorite(Long problemId);

    FavoritePageVo myFavorites(int page, int size);
}

package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FavoriteItemVo {
    private Long problemId;
    private String problemTitle;
    private String difficulty;
    private String supportLanguagesJson;
    private List<String> supportLanguages;
    private LocalDateTime favoritedAt;
}

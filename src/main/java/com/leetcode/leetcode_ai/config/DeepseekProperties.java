package com.leetcode.leetcode_ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ai.deepseek")
public class DeepseekProperties {
    private boolean enabled = true;
    private String baseUrl = "https://api.deepseek.com";
    private String apiKey;
    private String model = "deepseek-chat";
    private int timeoutMs = 6000;
    private int retryCount = 1;
}

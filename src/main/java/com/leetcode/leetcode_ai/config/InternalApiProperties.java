package com.leetcode.leetcode_ai.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "internal")
public class InternalApiProperties {

    @NotBlank
    private String judgeCallbackToken;
}

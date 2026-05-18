package com.leetcode.leetcode_ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.config.DeepseekProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeepseekAiTextServiceImpl implements AiTextService {

    private final DeepseekProperties deepseekProperties;
    private final ObjectMapper objectMapper;

    @Override
    public AiTextResult summarizeWrongQuestion(String judgeStatus, String compileLog, String knowledgePoints) {
        String prompt = "你是编程刷题助教。请用中文输出3句话，格式固定：\n"
                + "1) 可能原因：...\n"
                + "2) 涉及知识点：...\n"
                + "3) 修改建议：...\n"
                + "判题状态: " + safeText(judgeStatus) + "\n"
                + "知识点: " + safeText(knowledgePoints) + "\n"
                + "编译/运行信息: " + safeText(compileLog);
        return chat("wrong-summary", prompt);
    }

    @Override
    public AiTextResult recommendReason(String wrongContext, String candidateTitle, String candidateDifficulty) {
        String prompt = "你是编程刷题推荐助手。请用中文一句话说明为什么推荐该题，控制在40字内。\n"
                + "用户错题上下文: " + safeText(wrongContext) + "\n"
                + "候选题: " + safeText(candidateTitle) + "\n"
                + "难度: " + safeText(candidateDifficulty);
        return chat("recommend-reason", prompt);
    }

    private AiTextResult chat(String scene, String userPrompt) {
        if (!deepseekProperties.isEnabled() || !StringUtils.hasText(deepseekProperties.getApiKey())) {
            return null;
        }
        int totalAttempts = Math.max(1, deepseekProperties.getRetryCount() + 1);
        for (int attempt = 1; attempt <= totalAttempts; attempt++) {
            AiTextResult result = doChat(scene, userPrompt, attempt, totalAttempts);
            if (result != null) {
                return result;
            }
            if (attempt < totalAttempts) {
                sleepBriefly();
            }
        }
        return null;
    }

    private AiTextResult doChat(String scene, String userPrompt, int attempt, int totalAttempts) {
        try {
            String url = normalizeBaseUrl(deepseekProperties.getBaseUrl()) + "/chat/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(deepseekProperties.getApiKey().trim());

            Map<String, Object> body = Map.of(
                    "model", deepseekProperties.getModel(),
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a helpful assistant"),
                            Map.of("role", "user", "content", userPrompt)
                    ),
                    "stream", false
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate().postForEntity(url, request, String.class);
            if (!response.getStatusCode().is2xxSuccessful() || !StringUtils.hasText(response.getBody())) {
                return null;
            }
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode contentNode = root.path("choices").path(0).path("message").path("content");
            if (!contentNode.isTextual()) {
                log.warn("DeepSeek response content missing, scene={}, attempt={}/{}", scene, attempt, totalAttempts);
                return null;
            }
            String content = trimToLength(contentNode.asText(), 500);
            if (!StringUtils.hasText(content)) {
                log.warn("DeepSeek response empty content, scene={}, attempt={}/{}", scene, attempt, totalAttempts);
                return null;
            }
            String model = root.path("model").asText(deepseekProperties.getModel());
            return new AiTextResult(content, "AI", model);
        } catch (RestClientResponseException ex) {
            log.warn("DeepSeek HTTP error, scene={}, attempt={}/{}, status={}, body={}",
                    scene, attempt, totalAttempts, ex.getRawStatusCode(), trimToLength(ex.getResponseBodyAsString(), 400));
            return null;
        } catch (Exception ex) {
            log.warn("DeepSeek request failed, scene={}, attempt={}/{}, message={}",
                    scene, attempt, totalAttempts, ex.getMessage());
            return null;
        }
    }

    private RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(deepseekProperties.getTimeoutMs());
        factory.setReadTimeout(deepseekProperties.getTimeoutMs());
        return new RestTemplate(factory);
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (!StringUtils.hasText(baseUrl)) {
            return "https://api.deepseek.com";
        }
        String text = baseUrl.trim();
        return text.endsWith("/") ? text.substring(0, text.length() - 1) : text;
    }

    private String safeText(String text) {
        if (!StringUtils.hasText(text)) {
            return "无";
        }
        return trimToLength(text.trim(), 500);
    }

    private String trimToLength(String text, int maxLength) {
        if (!StringUtils.hasText(text)) {
            return text;
        }
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }

    private void sleepBriefly() {
        try {
            Thread.sleep(120L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

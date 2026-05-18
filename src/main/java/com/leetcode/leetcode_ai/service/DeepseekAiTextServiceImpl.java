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
        String prompt = "你是编程刷题助教。请用中文输出3行：\n"
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
        String prompt = "你是编程刷题推荐助手。请用中文1句话说明为什么推荐该题，控制在50字内。\n"
                + "用户错题上下文: " + safeText(wrongContext) + "\n"
                + "候选题: " + safeText(candidateTitle) + "\n"
                + "难度: " + safeText(candidateDifficulty);
        return chat("recommend-reason", prompt);
    }

    @Override
    public AiTextResult explainCode(String problemTitle,
                                    String language,
                                    String codeContent,
                                    String judgeStatus,
                                    String compileLog,
                                    String expectedOutput,
                                    String actualOutput) {
        String prompt = "你是算法代码讲解与纠错助手。请用中文输出5行：\n"
                + "1) 解题思路：...\n"
                + "2) 关键步骤：...\n"
                + "3) 时间复杂度：...\n"
                + "4) 空间复杂度：...\n"
                + "5) 错误分析与修改建议：...\n"
                + "题目: " + safeText(problemTitle) + "\n"
                + "语言: " + safeText(language) + "\n"
                + "判题状态: " + safeText(judgeStatus) + "\n"
                + "编译信息: " + safeText(compileLog) + "\n"
                + "期望输出: " + safeText(expectedOutput) + "\n"
                + "实际输出: " + safeText(actualOutput) + "\n"
                + "代码:\n" + safeText(codeContent);
        return chat("code-explain", prompt);
    }

    @Override
    public AiTextResult learningAdvice(String learningContext) {
        String prompt = "你是学习规划助教。请给出中文3行学习建议：\n"
                + "1) 当前主要问题：...\n"
                + "2) 接下来3天练习重点：...\n"
                + "3) 今日建议行动：...\n"
                + "上下文: " + safeText(learningContext);
        return chat("learning-advice", prompt);
    }

    private AiTextResult chat(String scene, String userPrompt) {
        if (!deepseekProperties.isEnabled() || !StringUtils.hasText(deepseekProperties.getApiKey())) {
            return null;
        }
        int totalAttempts = Math.max(1, deepseekProperties.getRetryCount() + 1);
        for (int attempt = 1; attempt <= totalAttempts; attempt++) {
            AiTextResult result = doChat(scene, userPrompt, attempt, totalAttempts);
            if (result != null) return result;
            if (attempt < totalAttempts) sleepBriefly();
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
            if (!response.getStatusCode().is2xxSuccessful() || !StringUtils.hasText(response.getBody())) return null;

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode contentNode = root.path("choices").path(0).path("message").path("content");
            if (!contentNode.isTextual()) return null;
            String content = trimToLength(contentNode.asText(), 700);
            if (!StringUtils.hasText(content)) return null;
            String model = root.path("model").asText(deepseekProperties.getModel());
            return new AiTextResult(content, "AI", model);
        } catch (RestClientResponseException ex) {
            log.warn("DeepSeek HTTP error, scene={}, attempt={}/{}, status={}", scene, attempt, totalAttempts, ex.getRawStatusCode());
            return null;
        } catch (Exception ex) {
            log.warn("DeepSeek request failed, scene={}, attempt={}/{}, message={}", scene, attempt, totalAttempts, ex.getMessage());
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
        if (!StringUtils.hasText(baseUrl)) return "https://api.deepseek.com";
        String text = baseUrl.trim();
        return text.endsWith("/") ? text.substring(0, text.length() - 1) : text;
    }

    private String safeText(String text) {
        if (!StringUtils.hasText(text)) return "无";
        return trimToLength(text.trim(), 700);
    }

    private String trimToLength(String text, int maxLength) {
        if (!StringUtils.hasText(text)) return text;
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }

    private void sleepBriefly() {
        try { Thread.sleep(120L); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }
}

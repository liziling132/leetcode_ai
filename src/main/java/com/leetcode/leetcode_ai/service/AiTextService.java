package com.leetcode.leetcode_ai.service;

public interface AiTextService {

    AiTextResult summarizeWrongQuestion(String judgeStatus, String compileLog, String knowledgePoints);

    AiTextResult recommendReason(String wrongContext, String candidateTitle, String candidateDifficulty);
}

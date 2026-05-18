package com.leetcode.leetcode_ai.service;

public interface AiTextService {

    AiTextResult summarizeWrongQuestion(String judgeStatus, String compileLog, String knowledgePoints);

    AiTextResult recommendReason(String wrongContext, String candidateTitle, String candidateDifficulty);

    AiTextResult explainCode(String problemTitle,
                             String language,
                             String codeContent,
                             String judgeStatus,
                             String compileLog,
                             String expectedOutput,
                             String actualOutput);

    AiTextResult learningAdvice(String learningContext);
}

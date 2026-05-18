package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.CreateSubmissionRequestDto;
import com.leetcode.leetcode_ai.dto.RunTestRequestDto;
import com.leetcode.leetcode_ai.vo.SubmissionCaseResultItemVo;
import com.leetcode.leetcode_ai.vo.SubmissionCaseResultPageVo;
import com.leetcode.leetcode_ai.vo.CreateSubmissionResponseVo;
import com.leetcode.leetcode_ai.vo.RunTestResponseVo;
import com.leetcode.leetcode_ai.vo.SubmissionDetailResponseVo;
import com.leetcode.leetcode_ai.vo.SubmissionPageVo;
import com.leetcode.leetcode_ai.vo.SubmissionVersionPageVo;
import com.leetcode.leetcode_ai.vo.AiCodeExplainVo;

import java.util.List;

public interface SubmissionService {

    CreateSubmissionResponseVo create(CreateSubmissionRequestDto request);

    RunTestResponseVo runTest(RunTestRequestDto request);

    SubmissionPageVo list(int page, int size, Long problemId, String judgeStatus, String language, String submittedFrom, String submittedTo);

    SubmissionVersionPageVo versionHistory(Long problemId, int page, int size);

    SubmissionDetailResponseVo detail(Long submissionId);

    SubmissionCaseResultPageVo caseResults(Long submissionId, int page, int size, boolean onlyFailed);

    AiCodeExplainVo explainCode(Long submissionId);
}

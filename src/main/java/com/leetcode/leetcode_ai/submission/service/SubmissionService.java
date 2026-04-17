package com.leetcode.leetcode_ai.submission.service;

import com.leetcode.leetcode_ai.submission.dto.CreateSubmissionRequest;
import com.leetcode.leetcode_ai.submission.dto.CreateSubmissionResponse;
import com.leetcode.leetcode_ai.submission.dto.SubmissionDetailResponse;

public interface SubmissionService {

    CreateSubmissionResponse create(CreateSubmissionRequest request);

    SubmissionDetailResponse detail(Long submissionId);
}

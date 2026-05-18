package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.TestcaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestcaseMapper {

    List<TestcaseEntity> findActiveByProblemId(@Param("problemId") Long problemId);
}

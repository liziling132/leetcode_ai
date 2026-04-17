package com.leetcode.leetcode_ai.testcase.mapper;

import com.leetcode.leetcode_ai.testcase.entity.Testcase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestcaseMapper {

    List<Testcase> findActiveByProblemId(@Param("problemId") Long problemId);
}

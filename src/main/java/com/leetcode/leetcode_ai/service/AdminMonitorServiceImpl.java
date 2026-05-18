package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.mapper.AdminMonitorMapper;
import com.leetcode.leetcode_ai.vo.AdminMonitorOverviewVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMonitorServiceImpl implements AdminMonitorService {

    private final AdminMonitorMapper adminMonitorMapper;

    @Override
    public AdminMonitorOverviewVo overview() {
        return new AdminMonitorOverviewVo(
                adminMonitorMapper.countUsers(),
                adminMonitorMapper.countProblems(),
                adminMonitorMapper.countSubmissions(),
                adminMonitorMapper.countPendingSubmissions(),
                adminMonitorMapper.countFailedSubmissions24h(),
                adminMonitorMapper.countRecommendations24h()
        );
    }
}

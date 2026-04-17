package com.leetcode.leetcode_ai.judge.queue;

import com.leetcode.leetcode_ai.judge.service.JudgeTriggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryJudgeQueueService implements JudgeQueueService {

    private final JudgeTriggerService judgeTriggerService;
    private final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();
    private final Set<Long> queuedSet = ConcurrentHashMap.newKeySet();
    private final Set<Long> processingSet = ConcurrentHashMap.newKeySet();

    @Override
    public boolean enqueue(Long submissionId) {
        if (submissionId == null || submissionId <= 0) {
            return false;
        }
        if (queuedSet.contains(submissionId) || processingSet.contains(submissionId)) {
            return false;
        }
        boolean offered = queue.offer(submissionId);
        if (offered) {
            queuedSet.add(submissionId);
        }
        return offered;
    }

    @Override
    public JudgeQueueStats stats() {
        return new JudgeQueueStats(queuedSet.size(), processingSet.size());
    }

    @Scheduled(fixedDelayString = "${judge.queue.poll-ms:500}")
    public void consume() {
        Long submissionId = queue.poll();
        if (submissionId == null) {
            return;
        }
        queuedSet.remove(submissionId);
        processingSet.add(submissionId);
        try {
            judgeTriggerService.trigger(submissionId);
        } catch (Exception ex) {
            log.error("Judge queue consume failed, submissionId={}", submissionId, ex);
        } finally {
            processingSet.remove(submissionId);
        }
    }
}

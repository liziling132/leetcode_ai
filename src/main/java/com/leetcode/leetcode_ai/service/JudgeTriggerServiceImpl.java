package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.JudgeCallbackRequestDto;
import com.leetcode.leetcode_ai.vo.JudgeTriggerResponseVo;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.entity.SubmissionCaseResultEntity;
import com.leetcode.leetcode_ai.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.entity.SubmissionEntity;
import com.leetcode.leetcode_ai.mapper.SubmissionCaseResultMapper;
import com.leetcode.leetcode_ai.mapper.SubmissionMapper;
import com.leetcode.leetcode_ai.entity.TestcaseEntity;
import com.leetcode.leetcode_ai.mapper.TestcaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JudgeTriggerServiceImpl implements JudgeTriggerService {

    private static final String STATUS_ACCEPTED = "ACCEPTED";
    private static final String STATUS_WRONG_ANSWER = "WRONG_ANSWER";
    private static final String STATUS_COMPILE_ERROR = "COMPILE_ERROR";
    private static final String STATUS_RUNTIME_ERROR = "RUNTIME_ERROR";
    private static final String STATUS_TIME_LIMIT_EXCEEDED = "TIME_LIMIT_EXCEEDED";
    private static final String STATUS_SYSTEM_ERROR = "SYSTEM_ERROR";
    private static final Duration COMPILE_TIMEOUT = Duration.ofSeconds(8);
    private static final Duration RUN_TIMEOUT = Duration.ofSeconds(2);

    private final SubmissionMapper submissionMapper;
    private final SubmissionCaseResultMapper submissionCaseResultMapper;
    private final ProblemMapper problemMapper;
    private final TestcaseMapper testcaseMapper;
    private final JudgeCallbackService judgeCallbackService;

    @Override
    public JudgeTriggerResponseVo trigger(Long submissionId) {
        SubmissionEntity SubmissionEntity = submissionMapper.findById(submissionId);
        if (SubmissionEntity == null) {
            return new JudgeTriggerResponseVo(submissionId, STATUS_SYSTEM_ERROR, 0, 0);
        }
        int marked = submissionMapper.markJudging(submissionId);
        if (marked == 0 && !"JUDGING".equals(SubmissionEntity.getJudgeStatus())) {
            return new JudgeTriggerResponseVo(submissionId, SubmissionEntity.getJudgeStatus(), SubmissionEntity.getPassedCaseCount(), SubmissionEntity.getTotalCaseCount());
        }

        ProblemEntity ProblemEntity = problemMapper.findOnlineById(SubmissionEntity.getProblemId());
        if (ProblemEntity == null) {
            return writeBack(submissionId, STATUS_SYSTEM_ERROR, "ProblemEntity not online or not found", 0, 0, 0);
        }
        if (!"java".equalsIgnoreCase(SubmissionEntity.getLanguage())) {
            return writeBack(submissionId, STATUS_SYSTEM_ERROR, "Only Java is supported in MVP judge", 0, 0, 0);
        }

        List<TestcaseEntity> testcases = testcaseMapper.findActiveByProblemId(ProblemEntity.getId());
        if (testcases.isEmpty()) {
            return writeBack(submissionId, STATUS_SYSTEM_ERROR, "No active testcases", 0, 0, 0);
        }

        List<SubmissionCaseResultEntity> caseResults = new java.util.ArrayList<>();
        Path workDir = null;
        try {
            workDir = Files.createTempDirectory("judge-java-");
            Path sourceFile = workDir.resolve("Solution.java");
            Files.writeString(sourceFile, SubmissionEntity.getCodeContent(), StandardCharsets.UTF_8);

            ExecResult compileResult = runProcess(
                    List.of(javacBin(), sourceFile.getFileName().toString()),
                    workDir,
                    null,
                    COMPILE_TIMEOUT
            );
            if (compileResult.timedOut) {
                persistCaseResults(submissionId, caseResults);
                return writeBack(submissionId, STATUS_COMPILE_ERROR, "Compile timeout", 0, 0, testcases.size());
            }
            if (compileResult.exitCode != 0) {
                persistCaseResults(submissionId, caseResults);
                return writeBack(submissionId, STATUS_COMPILE_ERROR, shortText(compileResult.output), 0, 0, testcases.size());
            }

            int passed = 0;
            int total = testcases.size();
            int totalRunTimeMs = 0;
            for (int i = 0; i < testcases.size(); i++) {
                TestcaseEntity TestcaseEntity = testcases.get(i);
                int caseIndex = i + 1;
                ExecResult runResult = runProcess(
                        List.of(javaBin(), "-cp", workDir.toString(), "Solution"),
                        workDir,
                        TestcaseEntity.getInputData(),
                        RUN_TIMEOUT
                );
                if (runResult.timedOut) {
                    caseResults.add(buildCaseResult(
                            submissionId, TestcaseEntity, caseIndex, STATUS_TIME_LIMIT_EXCEEDED,
                            runResult.timeMs, null, TestcaseEntity.getOutputData(), null, "Runtime timeout"
                    ));
                    persistCaseResults(submissionId, caseResults);
                    return writeBack(submissionId, STATUS_TIME_LIMIT_EXCEEDED, "Runtime timeout", totalRunTimeMs, passed, total);
                }
                if (runResult.exitCode != 0) {
                    caseResults.add(buildCaseResult(
                            submissionId, TestcaseEntity, caseIndex, STATUS_RUNTIME_ERROR,
                            runResult.timeMs, null, TestcaseEntity.getOutputData(), null, shortText(runResult.output)
                    ));
                    persistCaseResults(submissionId, caseResults);
                    return writeBack(submissionId, STATUS_RUNTIME_ERROR, shortText(runResult.output), totalRunTimeMs, passed, total);
                }

                totalRunTimeMs += runResult.timeMs;
                String actualOutput = runResult.output;
                if (!normalizeOutput(actualOutput).equals(normalizeOutput(TestcaseEntity.getOutputData()))) {
                    caseResults.add(buildCaseResult(
                            submissionId, TestcaseEntity, caseIndex, STATUS_WRONG_ANSWER,
                            runResult.timeMs, null, TestcaseEntity.getOutputData(), shortText(actualOutput), null
                    ));
                    persistCaseResults(submissionId, caseResults);
                    return writeBack(submissionId, STATUS_WRONG_ANSWER, null, totalRunTimeMs, passed, total);
                }
                caseResults.add(buildCaseResult(
                        submissionId, TestcaseEntity, caseIndex, STATUS_ACCEPTED,
                        runResult.timeMs, null, TestcaseEntity.getOutputData(), shortText(actualOutput), null
                ));
                passed++;
            }
            persistCaseResults(submissionId, caseResults);
            return writeBack(submissionId, STATUS_ACCEPTED, null, totalRunTimeMs, passed, testcases.size());
        } catch (Exception ex) {
            persistCaseResults(submissionId, caseResults);
            return writeBack(submissionId, STATUS_SYSTEM_ERROR, shortText(ex.getMessage()), 0, 0, testcases.size());
        } finally {
            deleteDirQuietly(workDir);
        }
    }

    private SubmissionCaseResultEntity buildCaseResult(Long submissionId,
                                                       TestcaseEntity testcaseEntity,
                                                       int caseIndex,
                                                       String status,
                                                       Integer runTimeMs,
                                                       Integer memoryKb,
                                                       String expectedOutput,
                                                       String actualOutput,
                                                       String errorMessage) {
        SubmissionCaseResultEntity row = new SubmissionCaseResultEntity();
        row.setSubmissionId(submissionId);
        row.setTestcaseId(testcaseEntity == null ? null : testcaseEntity.getId());
        row.setCaseIndex(caseIndex);
        row.setJudgeStatus(status);
        row.setRunTimeMs(runTimeMs == null || runTimeMs < 0 ? null : runTimeMs);
        row.setMemoryKb(memoryKb == null || memoryKb < 0 ? null : memoryKb);
        row.setExpectedOutput(shortText(expectedOutput));
        row.setActualOutput(shortText(actualOutput));
        row.setErrorMessage(shortText(errorMessage));
        return row;
    }

    private void persistCaseResults(Long submissionId, List<SubmissionCaseResultEntity> caseResults) {
        submissionCaseResultMapper.deleteBySubmissionId(submissionId);
        for (SubmissionCaseResultEntity row : caseResults) {
            submissionCaseResultMapper.insert(row);
        }
    }

    private JudgeTriggerResponseVo writeBack(Long submissionId, String status, String compileLog, int runTimeMs, int passed, int total) {
        JudgeCallbackRequestDto callbackRequest = new JudgeCallbackRequestDto();
        callbackRequest.setSubmissionId(submissionId);
        callbackRequest.setJudgeStatus(status);
        callbackRequest.setCompileLog(compileLog);
        callbackRequest.setRunTimeMs(Math.max(runTimeMs, 0));
        callbackRequest.setMemoryKb(null);
        callbackRequest.setPassedCaseCount(Math.max(passed, 0));
        callbackRequest.setTotalCaseCount(Math.max(total, 0));
        callbackRequest.setJudgeTraceId("local-" + UUID.randomUUID());
        judgeCallbackService.handleCallback(callbackRequest);
        return new JudgeTriggerResponseVo(submissionId, status, Math.max(passed, 0), Math.max(total, 0));
    }

    private ExecResult runProcess(List<String> command, Path workDir, String input, Duration timeout) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(workDir.toFile());
        builder.redirectErrorStream(true);

        long begin = System.nanoTime();
        Process process = builder.start();
        if (input != null) {
            try (OutputStream os = process.getOutputStream()) {
                os.write(input.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            process.getOutputStream().close();
        }

        boolean finished = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        long timeMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
        if (!finished) {
            process.destroyForcibly();
            return new ExecResult(true, -1, "", (int) timeMs);
        }
        String output;
        try (InputStream is = process.getInputStream()) {
            output = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        return new ExecResult(false, process.exitValue(), output, (int) timeMs);
    }

    private String javacBin() {
        String javaHome = System.getenv("JAVA_HOME");
        if (!StringUtils.hasText(javaHome)) {
            javaHome = System.getProperty("java.home");
        }
        return Path.of(javaHome, "bin", windowsExe("javac")).toString();
    }

    private String javaBin() {
        String javaHome = System.getenv("JAVA_HOME");
        if (!StringUtils.hasText(javaHome)) {
            javaHome = System.getProperty("java.home");
        }
        return Path.of(javaHome, "bin", windowsExe("java")).toString();
    }

    private String windowsExe(String binName) {
        String os = System.getProperty("os.name", "").toLowerCase();
        return os.contains("win") ? binName + ".exe" : binName;
    }

    private String normalizeOutput(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\r\n", "\n").trim();
    }

    private String shortText(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        String normalized = text.trim();
        return normalized.length() <= 1000 ? normalized : normalized.substring(0, 1000);
    }

    private void deleteDirQuietly(Path dir) {
        if (dir == null) {
            return;
        }
        try (var stream = Files.walk(dir)) {
            stream.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ignored) {
                }
            });
        } catch (IOException ignored) {
        }
    }

    private static class ExecResult {
        private final boolean timedOut;
        private final int exitCode;
        private final String output;
        private final int timeMs;

        private ExecResult(boolean timedOut, int exitCode, String output, int timeMs) {
            this.timedOut = timedOut;
            this.exitCode = exitCode;
            this.output = output;
            this.timeMs = timeMs;
        }
    }
}

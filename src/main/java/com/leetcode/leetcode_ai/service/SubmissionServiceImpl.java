package com.leetcode.leetcode_ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.service.JudgeQueueService;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.entity.SubmissionCaseResultEntity;
import com.leetcode.leetcode_ai.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.dto.CreateSubmissionRequestDto;
import com.leetcode.leetcode_ai.dto.RunTestRequestDto;
import com.leetcode.leetcode_ai.vo.CreateSubmissionResponseVo;
import com.leetcode.leetcode_ai.vo.RunTestResponseVo;
import com.leetcode.leetcode_ai.vo.AiCodeExplainVo;
import com.leetcode.leetcode_ai.vo.SubmissionCaseResultItemVo;
import com.leetcode.leetcode_ai.vo.SubmissionCaseResultPageVo;
import com.leetcode.leetcode_ai.vo.SubmissionDetailResponseVo;
import com.leetcode.leetcode_ai.vo.SubmissionPageVo;
import com.leetcode.leetcode_ai.vo.SubmissionVersionPageVo;
import com.leetcode.leetcode_ai.entity.SubmissionEntity;
import com.leetcode.leetcode_ai.mapper.SubmissionCaseResultMapper;
import com.leetcode.leetcode_ai.mapper.SubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private static final String STATUS_PENDING = "PENDING";
    private static final String RUN_STATUS_SUCCESS = "SUCCESS";
    private static final String RUN_STATUS_COMPILE_ERROR = "COMPILE_ERROR";
    private static final String RUN_STATUS_RUNTIME_ERROR = "RUNTIME_ERROR";
    private static final String RUN_STATUS_TIME_LIMIT_EXCEEDED = "TIME_LIMIT_EXCEEDED";
    private static final String RUN_STATUS_SYSTEM_ERROR = "SYSTEM_ERROR";
    private static final int MAX_LIST_PAGE_SIZE = 100;
    private static final int MAX_CASE_RESULT_PAGE_SIZE = 200;
    private static final Duration RUN_TEST_COMPILE_TIMEOUT = Duration.ofSeconds(8);
    private static final Duration RUN_TEST_EXEC_TIMEOUT = Duration.ofSeconds(3);
    private static final DateTimeFormatter[] DATE_TIME_FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    private final SubmissionMapper submissionMapper;
    private final ProblemMapper problemMapper;
    private final ObjectMapper objectMapper;
    private final JudgeQueueService judgeQueueService;
    private final SubmissionCaseResultMapper submissionCaseResultMapper;
    private final AiTextService aiTextService;

    @Override
    @Transactional
    public CreateSubmissionResponseVo create(CreateSubmissionRequestDto request) {
        String source = normalizeSource(request.getSource());
        ProblemEntity ProblemEntity = problemMapper.findOnlineById(request.getProblemId());
        if (ProblemEntity == null) {
            throw new BizException(404, "ProblemEntity not found");
        }

        if (!isLanguageSupported(request.getLanguage(), ProblemEntity.getSupportLanguages())) {
            throw new BizException(400, "Language not supported by this problem");
        }

        SubmissionEntity SubmissionEntity = new SubmissionEntity();
        SubmissionEntity.setUserId(currentUserId());
        SubmissionEntity.setProblemId(request.getProblemId());
        SubmissionEntity.setLanguage(request.getLanguage().trim());
        SubmissionEntity.setCodeContent(request.getCodeContent());
        SubmissionEntity.setJudgeStatus(STATUS_PENDING);
        submissionMapper.insert(SubmissionEntity);
        if ("submit".equals(source)) {
            judgeQueueService.enqueue(SubmissionEntity.getId());
        }
        return new CreateSubmissionResponseVo(SubmissionEntity.getId(), STATUS_PENDING);
    }

    @Override
    public RunTestResponseVo runTest(RunTestRequestDto request) {
        ProblemEntity ProblemEntity = problemMapper.findOnlineById(request.getProblemId());
        if (ProblemEntity == null) {
            throw new BizException(404, "ProblemEntity not found");
        }
        if (!isLanguageSupported(request.getLanguage(), ProblemEntity.getSupportLanguages())) {
            throw new BizException(400, "Language not supported by this problem");
        }
        if (!"java".equalsIgnoreCase(request.getLanguage().trim())) {
            throw new BizException(400, "Only Java is supported in MVP run test");
        }

        Path workDir = null;
        try {
            workDir = Files.createTempDirectory("run-test-java-");
            Path sourceFile = workDir.resolve("Solution.java");
            Files.writeString(sourceFile, request.getCodeContent(), StandardCharsets.UTF_8);

            ExecResult compileResult = runProcess(
                    List.of(
                            javacBin(),
                            "-J-Duser.language=en",
                            "-J-Duser.country=US",
                            "-XDrawDiagnostics",
                            "-encoding", "UTF-8",
                            sourceFile.getFileName().toString()
                    ),
                    workDir,
                    null,
                    RUN_TEST_COMPILE_TIMEOUT
            );
            if (compileResult.timedOut) {
                return new RunTestResponseVo(RUN_STATUS_COMPILE_ERROR, null, "Compile timeout", compileResult.timeMs);
            }
            if (compileResult.exitCode != 0) {
                return new RunTestResponseVo(RUN_STATUS_COMPILE_ERROR, null, trimText(compileResult.output, 4000), compileResult.timeMs);
            }

            ExecResult runResult = runProcess(
                    List.of(javaBin(), "-cp", workDir.toString(), "Solution"),
                    workDir,
                    request.getCustomInput(),
                    RUN_TEST_EXEC_TIMEOUT
            );
            if (runResult.timedOut) {
                return new RunTestResponseVo(RUN_STATUS_TIME_LIMIT_EXCEEDED, null, "Runtime timeout", runResult.timeMs);
            }
            if (runResult.exitCode != 0) {
                return new RunTestResponseVo(RUN_STATUS_RUNTIME_ERROR, null, trimText(runResult.output, 4000), runResult.timeMs);
            }
            return new RunTestResponseVo(RUN_STATUS_SUCCESS, trimText(runResult.output, 8000), null, runResult.timeMs);
        } catch (Exception ex) {
            return new RunTestResponseVo(RUN_STATUS_SYSTEM_ERROR, null, trimText(ex.getMessage(), 2000), null);
        } finally {
            deleteDirQuietly(workDir);
        }
    }

    @Override
    public SubmissionPageVo list(int page, int size, Long problemId, String judgeStatus, String language, String submittedFrom, String submittedTo) {
        if (page < 1 || size < 1 || size > MAX_LIST_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        Long userId = currentUserId();
        String normalizedJudgeStatus = normalizeJudgeStatus(judgeStatus);
        String normalizedLanguage = normalizeLanguage(language);
        LocalDateTime fromTime = parseDateTime(submittedFrom, true);
        LocalDateTime toTime = parseDateTime(submittedTo, false);
        if (fromTime != null && toTime != null && fromTime.isAfter(toTime)) {
            throw new BizException(400, "submittedFrom must be before submittedTo");
        }
        long total = submissionMapper.countByUserAndCondition(userId, problemId, normalizedJudgeStatus, normalizedLanguage, fromTime, toTime);
        if (total == 0) {
            return new SubmissionPageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        return new SubmissionPageVo(
                total,
                page,
                size,
                submissionMapper.findPageByUserAndCondition(userId, problemId, normalizedJudgeStatus, normalizedLanguage, fromTime, toTime, offset, size)
        );
    }

    @Override
    public SubmissionVersionPageVo versionHistory(Long problemId, int page, int size) {
        if (problemId == null || problemId <= 0) {
            throw new BizException(400, "Problem id is invalid");
        }
        if (page < 1 || size < 1 || size > MAX_LIST_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }

        Long userId = currentUserId();
        long total = submissionMapper.countByUserAndProblem(userId, problemId);
        if (total == 0) {
            return new SubmissionVersionPageVo(problemId, 0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        return new SubmissionVersionPageVo(
                problemId,
                total,
                page,
                size,
                submissionMapper.findVersionPageByUserAndProblem(userId, problemId, offset, size)
        );
    }

    @Override
    public SubmissionDetailResponseVo detail(Long submissionId) {
        SubmissionEntity SubmissionEntity = submissionMapper.findById(submissionId);
        if (SubmissionEntity == null) {
            throw new BizException(404, "SubmissionEntity not found");
        }
        if (!SubmissionEntity.getUserId().equals(currentUserId())) {
            throw new BizException(403, "Forbidden");
        }

        SubmissionDetailResponseVo response = new SubmissionDetailResponseVo();
        response.setId(SubmissionEntity.getId());
        response.setProblemId(SubmissionEntity.getProblemId());
        response.setLanguage(SubmissionEntity.getLanguage());
        response.setJudgeStatus(SubmissionEntity.getJudgeStatus());
        response.setJudgeTraceId(SubmissionEntity.getJudgeTraceId());
        response.setCompileLog(SubmissionEntity.getCompileLog());
        response.setRunTimeMs(SubmissionEntity.getRunTimeMs());
        response.setMemoryKb(SubmissionEntity.getMemoryKb());
        response.setPassedCaseCount(SubmissionEntity.getPassedCaseCount());
        response.setTotalCaseCount(SubmissionEntity.getTotalCaseCount());
        response.setSubmittedAt(SubmissionEntity.getSubmittedAt());
        response.setJudgedAt(SubmissionEntity.getJudgedAt());
        return response;
    }

    @Override
    public SubmissionCaseResultPageVo caseResults(Long submissionId, int page, int size, boolean onlyFailed) {
        SubmissionEntity SubmissionEntity = submissionMapper.findById(submissionId);
        if (SubmissionEntity == null) {
            throw new BizException(404, "SubmissionEntity not found");
        }
        if (!SubmissionEntity.getUserId().equals(currentUserId())) {
            throw new BizException(403, "Forbidden");
        }
        if (page < 1 || size < 1 || size > MAX_CASE_RESULT_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }

        long total = submissionCaseResultMapper.countBySubmissionId(submissionId, onlyFailed);
        if (total == 0) {
            return new SubmissionCaseResultPageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        List<SubmissionCaseResultEntity> rows = submissionCaseResultMapper.findPageBySubmissionId(submissionId, onlyFailed, offset, size);
        List<SubmissionCaseResultItemVo> list = rows.stream().map(this::toCaseResultItem).toList();
        return new SubmissionCaseResultPageVo(total, page, size, list);
    }

    @Override
    public AiCodeExplainVo explainCode(Long submissionId) {
        SubmissionEntity submissionEntity = submissionMapper.findById(submissionId);
        if (submissionEntity == null) {
            throw new BizException(404, "Submission not found");
        }
        if (!submissionEntity.getUserId().equals(currentUserId())) {
            throw new BizException(403, "Forbidden");
        }
        ProblemEntity problemEntity = problemMapper.findOnlineById(submissionEntity.getProblemId());
        String problemTitle = problemEntity == null ? "Unknown Problem" : problemEntity.getTitle();
        String expectedOutput = null;
        String actualOutput = null;
        if (!"ACCEPTED".equals(submissionEntity.getJudgeStatus())) {
            List<SubmissionCaseResultEntity> failed = submissionCaseResultMapper.findPageBySubmissionId(submissionId, true, 0, 1);
            if (!failed.isEmpty()) {
                expectedOutput = failed.get(0).getExpectedOutput();
                actualOutput = failed.get(0).getActualOutput();
            }
        }

        AiTextResult aiResult = aiTextService.explainCode(
                problemTitle,
                submissionEntity.getLanguage(),
                trimText(submissionEntity.getCodeContent(), 1200),
                submissionEntity.getJudgeStatus(),
                submissionEntity.getCompileLog(),
                expectedOutput,
                actualOutput
        );
        if (aiResult != null && StringUtils.hasText(aiResult.content())) {
            return new AiCodeExplainVo(submissionId, aiResult.content(), aiResult.source(), aiResult.model());
        }        String fallback = "解题思路：先明确输入输出并构造主流程。\n"
                + "关键步骤：按题意逐步处理数据，注意边界条件和空输入。\n"
                + "时间复杂度：通常与主要循环层数相关，请结合代码循环结构评估。\n"
                + "空间复杂度：主要来自额外数据结构与递归栈。\n"
                + "错误分析与修改建议：状态="
                + (submissionEntity.getJudgeStatus() == null ? "UNKNOWN" : submissionEntity.getJudgeStatus())
                + "，请优先检查编译日志，并对比失败测试点的期望输出与实际输出。";
        return new AiCodeExplainVo(submissionId, fallback, "RULE", null);
    }

    private SubmissionCaseResultItemVo toCaseResultItem(SubmissionCaseResultEntity row) {
        SubmissionCaseResultItemVo item = new SubmissionCaseResultItemVo();
        item.setId(row.getId());
        item.setTestcaseId(row.getTestcaseId());
        item.setCaseIndex(row.getCaseIndex());
        item.setJudgeStatus(row.getJudgeStatus());
        item.setRunTimeMs(row.getRunTimeMs());
        item.setMemoryKb(row.getMemoryKb());
        item.setExpectedOutput(row.getExpectedOutput());
        item.setActualOutput(row.getActualOutput());
        item.setErrorMessage(row.getErrorMessage());
        item.setCreatedAt(row.getCreatedAt());
        return item;
    }

    private String normalizeJudgeStatus(String judgeStatus) {
        if (!StringUtils.hasText(judgeStatus)) {
            return null;
        }
        return judgeStatus.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeLanguage(String language) {
        if (!StringUtils.hasText(language)) {
            return null;
        }
        return language.trim();
    }

    private LocalDateTime parseDateTime(String text, boolean isFrom) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        String normalized = text.trim();
        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(normalized, formatter);
            } catch (Exception ignored) {
            }
        }
        try {
            LocalDate date = LocalDate.parse(normalized, DateTimeFormatter.ISO_LOCAL_DATE);
            return isFrom ? date.atStartOfDay() : date.atTime(23, 59, 59);
        } catch (Exception ignored) {
        }
        throw new BizException(400, "Invalid datetime format: " + text);
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
            output = new String(is.readAllBytes(), Charset.defaultCharset());
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

    private String trimText(String text, int maxLength) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        String normalized = text.trim();
        return normalized.length() <= maxLength ? normalized : normalized.substring(0, maxLength);
    }

    private String normalizeSource(String source) {
        if (!StringUtils.hasText(source)) {
            throw new BizException(400, "Source is required");
        }
        String normalized = source.trim().toLowerCase(Locale.ROOT);
        if (!"run".equals(normalized) && !"submit".equals(normalized)) {
            throw new BizException(400, "Source must be run or submit");
        }
        return normalized;
    }

    private boolean isLanguageSupported(String language, String supportLanguagesJson) {
        if (!StringUtils.hasText(language)) {
            return false;
        }
        Set<String> supported = parseSupportLanguages(supportLanguagesJson).stream()
                .map(item -> item.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
        return supported.contains(language.trim().toLowerCase(Locale.ROOT));
    }

    private List<String> parseSupportLanguages(String jsonValue) {
        if (!StringUtils.hasText(jsonValue)) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(jsonValue, new TypeReference<List<String>>() {
            });
        } catch (Exception ignored) {
            return List.of(jsonValue);
        }
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
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

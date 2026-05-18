<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="id" label="提交ID" width="110" />
      <el-table-column prop="problemTitle" label="题目" />
      <el-table-column label="状态" width="150">
        <template #default="scope">
          <el-tag :type="statusTagType(scope.row.judgeStatus)">{{ statusText(scope.row.judgeStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="详情" width="80">
        <template #default="scope"><el-button link type="primary" @click="showDetail(scope.row.id)">查看</el-button></template>
      </el-table-column>
      <el-table-column label="测试点" width="90">
        <template #default="scope"><el-button link type="warning" @click="showCases(scope.row.id)">明细</el-button></template>
      </el-table-column>
      <el-table-column label="版本" width="90">
        <template #default="scope"><el-button link @click="showVersions(scope.row.problemId)">历史</el-button></template>
      </el-table-column>
      <el-table-column label="AI解释" width="180">
        <template #default="scope">
          <el-button link type="primary" :loading="explainLoadingId === scope.row.id" @click="explain(scope.row.id)">
            {{ explainLoadingId === scope.row.id ? 'AI智能分析中...' : '查看' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50]"
        :total="total"
        @current-change="load"
        @size-change="onSizeChange"
      />
    </div>

    <el-input v-model="aiText" type="textarea" :rows="8" style="margin-top: 12px" placeholder="点击“查看”显示 AI 代码解释（含错误分析）" />

    <el-dialog v-model="detailVisible" title="提交详情" width="780px"><pre>{{ JSON.stringify(detail, null, 2) }}</pre></el-dialog>

    <el-dialog v-model="caseVisible" title="测试点明细" width="900px">
      <el-table :data="caseList">
        <el-table-column prop="caseIndex" label="#" width="70" />
        <el-table-column prop="judgeStatus" label="状态" width="140" />
        <el-table-column prop="expectedOutput" label="期望输出" />
        <el-table-column prop="actualOutput" label="实际输出" />
      </el-table>
      <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
        <el-pagination v-model:current-page="casePage" v-model:page-size="caseSize" layout="total, prev, pager, next" :total="caseTotal" @current-change="loadCases" />
      </div>
    </el-dialog>

    <el-dialog v-model="versionVisible" title="历史版本" width="900px">
      <el-table :data="versionList">
        <el-table-column prop="submissionId" label="提交ID" width="100" />
        <el-table-column prop="judgeStatus" label="状态" width="140" />
        <el-table-column prop="language" label="语言" width="90" />
        <el-table-column prop="submittedAt" label="提交时间" width="180" />
        <el-table-column prop="codeContent" label="代码" show-overflow-tooltip />
      </el-table>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const list = ref<any[]>([])
const aiText = ref('')
const page = ref(1)
const size = ref(20)
const total = ref(0)
const explainLoadingId = ref<number | null>(null)

const detailVisible = ref(false)
const detail = ref<any>({})

const caseVisible = ref(false)
const caseList = ref<any[]>([])
const caseTotal = ref(0)
const casePage = ref(1)
const caseSize = ref(10)
const currentSubmissionId = ref<number | null>(null)

const versionVisible = ref(false)
const versionList = ref<any[]>([])

let timer: number | null = null

const statusText = (status: string) => ({
  PENDING: '待判题', JUDGING: '判题中', ACCEPTED: '通过', WRONG_ANSWER: '答案错误',
  COMPILE_ERROR: '编译错误', RUNTIME_ERROR: '运行错误', TIME_LIMIT_EXCEEDED: '超时',
  MEMORY_LIMIT_EXCEEDED: '超内存', SYSTEM_ERROR: '系统错误'
}[status] || status || '-')

const statusTagType = (status: string) => status === 'ACCEPTED' ? 'success' : (status === 'PENDING' || status === 'JUDGING' ? 'warning' : 'info')

const hasRunning = () => list.value.some((x) => x.judgeStatus === 'PENDING' || x.judgeStatus === 'JUDGING')
const startPollingIfNeeded = () => {
  if (!hasRunning() || timer != null) return
  timer = window.setInterval(async () => {
    await load(false)
    if (!hasRunning() && timer != null) {
      window.clearInterval(timer)
      timer = null
    }
  }, 2000)
}

const load = async (managePolling = true) => {
  const data: any = await api.submissions({ page: page.value, size: size.value })
  list.value = data.list || []
  total.value = data.total || 0
  if (managePolling) startPollingIfNeeded()
}

const onSizeChange = async () => { page.value = 1; await load() }
const showDetail = async (id: number) => { detail.value = await api.submissionDetail(id); detailVisible.value = true }

const loadCases = async () => {
  if (!currentSubmissionId.value) return
  const data: any = await api.submissionCaseResults(currentSubmissionId.value, { page: casePage.value, size: caseSize.value })
  caseList.value = data.list || []
  caseTotal.value = data.total || 0
}
const showCases = async (id: number) => {
  currentSubmissionId.value = id
  casePage.value = 1
  await loadCases()
  caseVisible.value = true
}

const showVersions = async (problemId: number) => {
  const data: any = await api.submissionVersions(problemId, { page: 1, size: 20 })
  versionList.value = data.list || []
  versionVisible.value = true
}

const explain = async (id: number) => {
  explainLoadingId.value = id
  try {
    const data: any = await api.explain(id)
    aiText.value = data.explanation || ''
  } catch (e: any) {
    ElMessage.error(e?.message || 'AI解释失败')
  } finally {
    explainLoadingId.value = null
  }
}

onMounted(load)
onUnmounted(() => { if (timer != null) window.clearInterval(timer) })
</script>

<template>
  <el-row :gutter="12">
    <el-col :span="12">
      <el-card>
        <template #header>学习数据</template>
        <el-row :gutter="10" style="margin-bottom: 10px">
          <el-col :span="8"><el-statistic title="总提交" :value="stats.totalSubmissions || 0" /></el-col>
          <el-col :span="8"><el-statistic title="已通过" :value="stats.acceptedSubmissions || 0" /></el-col>
          <el-col :span="8"><el-statistic title="通过率" :value="stats.acceptanceRate || 0" suffix="%" /></el-col>
        </el-row>

        <el-progress :percentage="safeRate" :stroke-width="16" status="success" style="margin-bottom: 12px" />

        <el-row :gutter="10" style="margin-bottom: 10px">
          <el-col :span="12"><el-statistic title="近7天总提交" :value="recentTotal" /></el-col>
          <el-col :span="12"><el-statistic title="近7天日均提交" :value="recentAvg" /></el-col>
        </el-row>

        <el-divider content-position="left">近7天提交趋势</el-divider>
        <el-table :data="stats.recent7Days || []" size="small" border>
          <el-table-column prop="date" label="日期" width="140" />
          <el-table-column prop="submissions" label="提交数" width="90" />
          <el-table-column label="趋势" min-width="180">
            <template #default="scope">
              <div class="trend-bar-wrap">
                <div class="trend-bar" :style="{ width: trendWidth(scope.row.submissions) + '%' }"></div>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <el-divider content-position="left">薄弱知识点 Top</el-divider>
        <el-table :data="weakPoints" size="small" border>
          <el-table-column type="index" label="#" width="50" />
          <el-table-column prop="tagName" label="知识点" />
          <el-table-column prop="wrongCount" label="错误次数" width="100" />
          <el-table-column label="占比" min-width="180">
            <template #default="scope">
              <el-progress :percentage="weakRate(scope.row.wrongCount)" :stroke-width="12" color="#e6a23c" />
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>

    <el-col :span="12">
      <el-card>
        <template #header>
          <div style="display:flex;justify-content:space-between;align-items:center;">
            <span>AI学习建议</span>
            <el-button type="primary" :loading="adviceLoading" @click="loadAdvice">
              {{ adviceLoading ? 'AI智能分析中...' : '刷新建议' }}
            </el-button>
          </div>
        </template>

        <el-skeleton :loading="adviceLoading" animated :rows="6">
          <template #default>
            <el-alert
              v-if="!adviceTouched"
              title="点击“刷新建议”后再生成学习建议。"
              type="info"
              show-icon
              :closable="false"
              style="margin-bottom:10px"
            />
            <template v-else>
              <el-alert :title="`来源：${adviceSource}`" type="info" show-icon :closable="false" style="margin-bottom:10px" />
              <div style="white-space: pre-wrap; line-height: 1.8">{{ adviceText || '暂无建议' }}</div>
            </template>
          </template>
        </el-skeleton>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const stats = reactive<any>({ totalSubmissions: 0, acceptedSubmissions: 0, acceptanceRate: 0, recent7Days: [] })
const weakPoints = ref<any[]>([])

const adviceLoading = ref(false)
const adviceText = ref('')
const adviceSource = ref('')
const adviceTouched = ref(false)

const safeRate = computed(() => {
  const v = Number(stats.acceptanceRate || 0)
  if (!Number.isFinite(v)) return 0
  return Math.max(0, Math.min(100, Number(v.toFixed(2))))
})

const recentTotal = computed(() => (stats.recent7Days || []).reduce((sum: number, d: any) => sum + Number(d.submissions || 0), 0))
const recentAvg = computed(() => {
  const days = (stats.recent7Days || []).length
  if (!days) return 0
  return Number((recentTotal.value / days).toFixed(2))
})
const recentMax = computed(() => Math.max(1, ...(stats.recent7Days || []).map((d: any) => Number(d.submissions || 0))))
const trendWidth = (v: number) => Math.max(4, Math.round((Number(v || 0) / recentMax.value) * 100))

const weakTotal = computed(() => weakPoints.value.reduce((sum, x) => sum + Number(x.wrongCount || 0), 0))
const weakRate = (count: number) => {
  if (!weakTotal.value) return 0
  return Math.min(100, Math.round((Number(count || 0) / weakTotal.value) * 100))
}

const loadStats = async () => {
  const s: any = await api.stats()
  const w: any = await api.weakPoints({ size: 8 })
  Object.assign(stats, s || {})
  weakPoints.value = w?.list || []
}

const loadAdvice = async () => {
  adviceTouched.value = true
  adviceLoading.value = true
  try {
    const a: any = await api.learningAdvice()
    adviceText.value = a.advice || ''
    adviceSource.value = a.aiSource || 'RULE'
  } catch (e: any) {
    ElMessage.error(e?.message || '获取学习建议失败')
  } finally {
    adviceLoading.value = false
  }
}

onMounted(async () => {
  await loadStats()
})
</script>

<style scoped>
.trend-bar-wrap {
  width: 100%;
  height: 10px;
  background: #f2f6fc;
  border-radius: 999px;
  overflow: hidden;
}

.trend-bar {
  height: 10px;
  background: #409eff;
  border-radius: 999px;
}
</style>

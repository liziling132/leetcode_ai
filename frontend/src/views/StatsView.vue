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

        <el-divider content-position="left">近7天提交趋势</el-divider>
        <el-table :data="stats.recent7Days || []" size="small" border>
          <el-table-column prop="date" label="日期" width="140" />
          <el-table-column prop="submissions" label="提交数" />
        </el-table>

        <el-divider content-position="left">薄弱知识点</el-divider>
        <el-tag
          v-for="(w, idx) in weakPoints"
          :key="idx"
          type="warning"
          style="margin-right: 8px; margin-bottom: 8px"
        >
          {{ w.tagName }} ({{ w.wrongCount }})
        </el-tag>
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
            <el-alert :title="`来源：${adviceSource}`" type="info" show-icon :closable="false" style="margin-bottom:10px" />
            <div style="white-space: pre-wrap; line-height: 1.8">{{ adviceText || '暂无建议' }}</div>
          </template>
        </el-skeleton>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const stats = reactive<any>({ totalSubmissions: 0, acceptedSubmissions: 0, acceptanceRate: 0, recent7Days: [] })
const weakPoints = ref<any[]>([])

const adviceLoading = ref(false)
const adviceText = ref('')
const adviceSource = ref('')

const loadStats = async () => {
  const s: any = await api.stats()
  const w: any = await api.weakPoints({ size: 8 })
  Object.assign(stats, s || {})
  weakPoints.value = (w?.list || [])
}

const loadAdvice = async () => {
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
  await loadAdvice()
})
</script>

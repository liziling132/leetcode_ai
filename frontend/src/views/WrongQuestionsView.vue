<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="problemId" label="题目ID" width="100" />
      <el-table-column prop="problemTitle" label="题目" />
      <el-table-column prop="wrongCount" label="错题次数" width="120" />
      <el-table-column label="错误类型" width="160">
        <template #default="scope">{{ statusText(scope.row.lastWrongType) }}</template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { api } from '@/api'

const list = ref<any[]>([])

const statusText = (status: string) => {
  const m: Record<string, string> = {
    PENDING: '待判题',
    JUDGING: '判题中',
    ACCEPTED: '通过',
    WRONG_ANSWER: '答案错误',
    COMPILE_ERROR: '编译错误',
    RUNTIME_ERROR: '运行错误',
    TIME_LIMIT_EXCEEDED: '超时',
    MEMORY_LIMIT_EXCEEDED: '超内存',
    SYSTEM_ERROR: '系统错误'
  }
  return m[status] || status || '-'
}

onMounted(async () => {
  const data: any = await api.wrongQuestions({ page: 1, size: 20 })
  list.value = data.list || []
})
</script>

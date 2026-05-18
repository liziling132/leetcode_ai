<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="problemId" label="题目ID" width="100" />
      <el-table-column prop="problemTitle" label="题目" />
      <el-table-column prop="wrongCount" label="错题次数" width="120" />
      <el-table-column prop="lastWrongType" label="错误类型" width="160" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { api } from '@/api'
const list = ref<any[]>([])
onMounted(async () => { const data: any = await api.wrongQuestions({ page: 1, size: 20 }); list.value = data.list || [] })
</script>

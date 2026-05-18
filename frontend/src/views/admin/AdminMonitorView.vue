<template>
  <el-row :gutter="12">
    <el-col :span="8"><el-card>用户总数：{{ data.totalUsers }}</el-card></el-col>
    <el-col :span="8"><el-card>题目总数：{{ data.totalProblems }}</el-card></el-col>
    <el-col :span="8"><el-card>提交总数：{{ data.totalSubmissions }}</el-card></el-col>
    <el-col :span="8"><el-card>错题总数：{{ data.totalWrongQuestions }}</el-card></el-col>
    <el-col :span="8"><el-card>24h 提交：{{ data.submissionCount24h }}</el-card></el-col>
    <el-col :span="8"><el-card>24h 推荐：{{ data.recommendationCount24h }}</el-card></el-col>
  </el-row>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { api } from '@/api'

const data = reactive<any>({})
const load = async () => {
  const r: any = await api.adminMonitorOverview()
  Object.assign(data, r || {})
}
load()
</script>

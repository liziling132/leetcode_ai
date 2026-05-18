<template>
  <el-row :gutter="12">
    <el-col :span="12"><el-card><pre>{{ stats }}</pre></el-card></el-col>
    <el-col :span="12"><el-card><pre>{{ advice }}</pre></el-card></el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { api } from '@/api'
const stats = ref('')
const advice = ref('')
onMounted(async () => {
  const s: any = await api.stats()
  const w: any = await api.weakPoints({ size: 5 })
  const a: any = await api.learningAdvice()
  stats.value = JSON.stringify({ stats: s, weakPoints: w }, null, 2)
  advice.value = a.advice
})
</script>

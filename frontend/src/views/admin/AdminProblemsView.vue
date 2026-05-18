<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="difficulty" label="难度" width="120" />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column prop="tagNames" label="标签" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { api } from '@/api'
const list = ref<any[]>([])
const load = async () => {
  const data: any = await api.adminProblems({ page: 1, size: 20 })
  list.value = data.list || []
}
load()
</script>

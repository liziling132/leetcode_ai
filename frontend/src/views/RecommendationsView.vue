<template>
  <el-card>
    <el-button type="primary" @click="refresh">生成推荐</el-button>
    <el-table :data="list" style="margin-top:12px">
      <el-table-column prop="problemId" label="题目ID" width="100" />
      <el-table-column prop="problemTitle" label="题目" />
      <el-table-column prop="reasonText" label="推荐理由" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { api } from '@/api'
const list = ref<any[]>([])
const refresh = async () => { const data: any = await api.recommendations({ size: 5 }); list.value = data.items || data.list || [] }
onMounted(refresh)
</script>

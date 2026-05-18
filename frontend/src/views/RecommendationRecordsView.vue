<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="id" label="记录ID" width="90" />
      <el-table-column prop="problemId" label="题目ID" width="90" />
      <el-table-column prop="problemTitle" label="题目" min-width="220" />
      <el-table-column prop="difficulty" label="难度" width="100" />
      <el-table-column prop="reasonText" label="推荐理由" min-width="280" />
      <el-table-column prop="recommendedAt" label="推荐时间" width="180" />
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination v-model:current-page="page" v-model:page-size="size" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50]" :total="total" @current-change="load" @size-change="onSizeChange" />
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { api } from '@/api'

const list = ref<any[]>([])
const page = ref(1)
const size = ref(20)
const total = ref(0)

const load = async () => {
  const data: any = await api.recommendRecords({ page: page.value, size: size.value })
  list.value = data.list || []
  total.value = data.total || 0
}
const onSizeChange = async () => { page.value = 1; await load() }

onMounted(load)
</script>

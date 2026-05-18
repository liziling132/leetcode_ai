<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="problemId" label="题目ID" width="100" />
      <el-table-column prop="problemTitle" label="题目" />
      <el-table-column prop="difficulty" label="难度" width="120" />
      <el-table-column prop="favoritedAt" label="收藏时间" width="200" />
      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button link type="primary" @click="$router.push(`/problems/${scope.row.problemId}`)">去做题</el-button>
          <el-button link type="danger" @click="remove(scope.row.problemId)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination v-model:current-page="page" v-model:page-size="size" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50]" :total="total" @current-change="load" @size-change="onSizeChange" />
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const list = ref<any[]>([])
const page = ref(1)
const size = ref(20)
const total = ref(0)

const load = async () => {
  const data: any = await api.favorites({ page: page.value, size: size.value })
  list.value = data.list || []
  total.value = data.total || 0
}
const onSizeChange = async () => { page.value = 1; await load() }

const remove = async (id: number) => {
  await api.unfavorite(id)
  ElMessage.success('已取消收藏')
  await load()
}

onMounted(load)
</script>

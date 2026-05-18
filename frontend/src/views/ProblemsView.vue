<template>
  <el-card>
    <el-form inline>
      <el-form-item label="关键字"><el-input v-model="q.keyword" /></el-form-item>
      <el-form-item label="难度"><el-input v-model="q.difficulty" placeholder="EASY/MEDIUM/HARD" /></el-form-item>
      <el-button type="primary" @click="load">查询</el-button>
    </el-form>
    <el-table :data="list" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="题目" />
      <el-table-column prop="difficulty" label="难度" width="120" />
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" @click="$router.push(`/problems/${scope.row.id}`)">进入</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { api } from '@/api'

const q = reactive({ page: 1, size: 20, keyword: '', difficulty: '' })
const list = ref<any[]>([])

const load = async () => {
  const data: any = await api.problems(q)
  list.value = data.list || []
}

onMounted(load)
</script>

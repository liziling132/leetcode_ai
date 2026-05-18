<template>
  <el-card>
    <el-form inline>
      <el-form-item label="关键字"><el-input v-model="keyword" /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="status" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="load">查询</el-button>
    </el-form>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button size="small" @click="changeStatus(scope.row.id, 1)">启用</el-button>
          <el-button size="small" type="warning" @click="changeStatus(scope.row.id, 0)">禁用</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const keyword = ref('')
const status = ref<number | undefined>()
const list = ref<any[]>([])

const load = async () => {
  const data: any = await api.adminUsers({ page: 1, size: 20, keyword: keyword.value || undefined, status: status.value })
  list.value = data.list || []
}

const changeStatus = async (id: number, v: number) => {
  await api.adminUpdateUserStatus(id, v)
  ElMessage.success('更新成功')
  await load()
}

load()
</script>

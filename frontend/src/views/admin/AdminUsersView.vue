<template>
  <el-card>
    <el-form inline>
      <el-form-item label="关键字"><el-input v-model="keyword" clearable /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="status" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="onSearch">查询</el-button>
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

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination v-model:current-page="page" v-model:page-size="size" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50]" :total="total" @current-change="load" @size-change="onSizeChange" />
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const keyword = ref('')
const status = ref<number | undefined>()
const list = ref<any[]>([])
const page = ref(1)
const size = ref(20)
const total = ref(0)

const load = async () => {
  const data: any = await api.adminUsers({ page: page.value, size: size.value, keyword: keyword.value || undefined, status: status.value })
  list.value = data.list || []
  total.value = data.total || 0
}

const onSearch = async () => { page.value = 1; await load() }
const onSizeChange = async () => { page.value = 1; await load() }

const changeStatus = async (id: number, v: number) => {
  await api.adminUpdateUserStatus(id, v)
  ElMessage.success('更新成功')
  await load()
}

onMounted(load)
</script>

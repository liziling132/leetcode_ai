<template>
  <el-card>
    <el-form inline>
      <el-form-item label="标签名"><el-input v-model="tagName" /></el-form-item>
      <el-form-item label="类型"><el-input v-model="tagType" /></el-form-item>
      <el-button type="primary" @click="createTag">新增</el-button>
      <el-button @click="load">刷新</el-button>
    </el-form>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="tagName" label="标签名" />
      <el-table-column prop="tagType" label="类型" />
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button link type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const list = ref<any[]>([])
const tagName = ref('')
const tagType = ref('knowledge')

const load = async () => {
  const data: any = await api.adminTags({ page: 1, size: 50 })
  list.value = data.list || []
}

const createTag = async () => {
  if (!tagName.value.trim()) return
  await api.adminCreateTag({ tagName: tagName.value.trim(), tagType: tagType.value || 'knowledge' })
  tagName.value = ''
  ElMessage.success('新增成功')
  await load()
}

const remove = async (id: number) => {
  await api.adminDeleteTag(id, true)
  ElMessage.success('删除成功')
  await load()
}

load()
</script>

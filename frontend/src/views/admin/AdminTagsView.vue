<template>
  <el-card>
    <el-form inline>
      <el-form-item label="关键字"><el-input v-model="q.keyword" clearable /></el-form-item>
      <el-form-item label="类型"><el-input v-model="q.tagType" placeholder="knowledge/algorithm" clearable /></el-form-item>
      <el-button type="primary" @click="onSearch">查询</el-button>
      <el-button type="success" @click="openCreate">新增</el-button>
    </el-form>

    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="tagName" label="标签名" />
      <el-table-column prop="tagType" label="类型" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button link type="primary" @click="openEdit(scope.row)">编辑</el-button>
          <el-button link type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination v-model:current-page="q.page" v-model:page-size="q.size" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50]" :total="total" @current-change="load" @size-change="onSizeChange" />
    </div>

    <el-dialog v-model="visible" :title="editingId ? '编辑标签' : '新增标签'">
      <el-form label-width="90px">
        <el-form-item label="标签名"><el-input v-model="form.tagName" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.tagType" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const q = reactive<any>({ page: 1, size: 20, keyword: '', tagType: '' })
const list = ref<any[]>([])
const total = ref(0)

const visible = ref(false)
const editingId = ref<number | null>(null)
const form = reactive<any>({ tagName: '', tagType: 'knowledge' })

const load = async () => {
  const data: any = await api.adminTags(q)
  list.value = data.list || []
  total.value = data.total || 0
}
const onSearch = async () => { q.page = 1; await load() }
const onSizeChange = async () => { q.page = 1; await load() }

const openCreate = () => {
  editingId.value = null
  Object.assign(form, { tagName: '', tagType: 'knowledge' })
  visible.value = true
}
const openEdit = (row: any) => {
  editingId.value = row.id
  Object.assign(form, { tagName: row.tagName, tagType: row.tagType })
  visible.value = true
}
const save = async () => {
  if (editingId.value) await api.adminUpdateTag(editingId.value, form)
  else await api.adminCreateTag(form)
  ElMessage.success('保存成功')
  visible.value = false
  await load()
}
const remove = async (id: number) => {
  await api.adminDeleteTag(id, true)
  ElMessage.success('删除成功')
  await load()
}

onMounted(load)
</script>

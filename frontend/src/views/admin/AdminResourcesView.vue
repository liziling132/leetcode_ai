<template>
  <el-card>
    <el-form inline>
      <el-form-item label="关键字"><el-input v-model="q.keyword" clearable /></el-form-item>
      <el-form-item label="类型"><el-input v-model="q.resourceType" clearable /></el-form-item>
      <el-form-item label="状态"><el-select v-model="q.status" clearable style="width: 120px"><el-option label="启用" :value="1" /><el-option label="禁用" :value="0" /></el-select></el-form-item>
      <el-button type="primary" @click="onSearch">查询</el-button>
      <el-button type="success" @click="openCreate">新增资源</el-button>
    </el-form>

    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="resourceType" label="类型" width="120" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="url" label="URL" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button link type="primary" @click="openEdit(scope.row.id)">编辑</el-button>
          <el-button link @click="showDetail(scope.row.id)">详情</el-button>
          <el-button link type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination v-model:current-page="q.page" v-model:page-size="q.size" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50]" :total="total" @current-change="load" @size-change="onSizeChange" />
    </div>

    <el-dialog v-model="visible" :title="editingId ? '编辑资源' : '新增资源'" width="760px">
      <el-form label-width="90px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.resourceType" /></el-form-item>
        <el-form-item label="URL"><el-input v-model="form.url" /></el-form-item>
        <el-form-item label="知识点"><el-input v-model="knowledgeText" placeholder="逗号分隔" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status"><el-option label="启用" :value="1" /><el-option label="禁用" :value="0" /></el-select></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="资源详情" width="760px"><pre>{{ JSON.stringify(detail, null, 2) }}</pre></el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const q = reactive<any>({ page: 1, size: 20, keyword: '', resourceType: '', status: undefined })
const list = ref<any[]>([])
const total = ref(0)

const visible = ref(false)
const editingId = ref<number | null>(null)
const form = reactive<any>({ resourceType: 'article', title: '', url: '', status: 1 })
const knowledgeText = ref('')

const detailVisible = ref(false)
const detail = ref<any>({})

const load = async () => {
  const data: any = await api.adminResources(q)
  list.value = data.list || []
  total.value = data.total || 0
}
const onSearch = async () => { q.page = 1; await load() }
const onSizeChange = async () => { q.page = 1; await load() }

const openCreate = () => {
  editingId.value = null
  Object.assign(form, { resourceType: 'article', title: '', url: '', status: 1 })
  knowledgeText.value = ''
  visible.value = true
}

const openEdit = async (id: number) => {
  const d: any = await api.adminResourceDetail(id)
  editingId.value = id
  Object.assign(form, d)
  knowledgeText.value = (d.knowledgePoints || []).join(',')
  visible.value = true
}

const showDetail = async (id: number) => {
  detail.value = await api.adminResourceDetail(id)
  detailVisible.value = true
}

const save = async () => {
  const payload = {
    ...form,
    knowledgePoints: knowledgeText.value.split(',').map((x) => x.trim()).filter(Boolean)
  }
  if (editingId.value) await api.adminUpdateResource(editingId.value, payload)
  else await api.adminCreateResource(payload)
  ElMessage.success('保存成功')
  visible.value = false
  await load()
}

const remove = async (id: number) => {
  await api.adminDeleteResource(id)
  ElMessage.success('删除成功')
  await load()
}

onMounted(load)
</script>

<template>
  <el-card>
    <el-form inline>
      <el-form-item label="关键字"><el-input v-model="q.keyword" clearable /></el-form-item>
      <el-form-item label="难度"><el-select v-model="q.difficulty" clearable style="width: 120px"><el-option label="EASY" value="EASY" /><el-option label="MEDIUM" value="MEDIUM" /><el-option label="HARD" value="HARD" /></el-select></el-form-item>
      <el-button type="primary" @click="onSearch">查询</el-button>
      <el-button type="success" @click="openCreate">新增题目</el-button>
    </el-form>

    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="difficulty" label="难度" width="120" />
      <el-table-column prop="status" label="状态" width="90" />
      <el-table-column prop="tagNames" label="标签" />
      <el-table-column label="操作" width="260">
        <template #default="scope">
          <el-button link type="primary" @click="openEdit(scope.row.id)">编辑</el-button>
          <el-button link type="warning" @click="openTestcases(scope.row.id)">测试用例</el-button>
          <el-button link type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination v-model:current-page="q.page" v-model:page-size="q.size" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50]" :total="total" @current-change="load" @size-change="onSizeChange" />
    </div>

    <el-dialog v-model="editVisible" :title="editingId ? '编辑题目' : '新增题目'" width="900px">
      <el-form label-width="120px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="难度"><el-select v-model="form.difficulty"><el-option label="EASY" value="EASY" /><el-option label="MEDIUM" value="MEDIUM" /><el-option label="HARD" value="HARD" /></el-select></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status"><el-option label="启用" :value="1" /><el-option label="禁用" :value="0" /></el-select></el-form-item>
        <el-form-item label="支持语言"><el-input v-model="languagesText" placeholder="逗号分隔，例如 java,python,cpp" /></el-form-item>
        <el-form-item label="知识点"><el-input v-model="knowledgeText" placeholder="逗号分隔" /></el-form-item>
        <el-form-item label="题目描述"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="输入说明"><el-input v-model="form.inputDesc" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="输出说明"><el-input v-model="form.outputDesc" type="textarea" :rows="2" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="testcaseVisible" title="替换测试用例" width="900px">
      <el-input v-model="testcaseText" type="textarea" :rows="12" placeholder='JSON数组，例如[{"inputData":"1 2","expectedOutput":"3","caseOrder":1}]' />
      <template #footer>
        <el-button @click="testcaseVisible = false">取消</el-button>
        <el-button type="primary" @click="saveTestcases">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const q = reactive<any>({ page: 1, size: 20, keyword: '', difficulty: '' })
const list = ref<any[]>([])
const total = ref(0)

const editVisible = ref(false)
const editingId = ref<number | null>(null)
const form = reactive<any>({ title: '', content: '', inputDesc: '', outputDesc: '', difficulty: 'EASY', status: 1 })
const languagesText = ref('java,python,cpp')
const knowledgeText = ref('array')

const testcaseVisible = ref(false)
const testcaseProblemId = ref<number | null>(null)
const testcaseText = ref('[]')

const load = async () => {
  const data: any = await api.adminProblems(q)
  list.value = data.list || []
  total.value = data.total || 0
}
const onSearch = async () => { q.page = 1; await load() }
const onSizeChange = async () => { q.page = 1; await load() }

const openCreate = () => {
  editingId.value = null
  Object.assign(form, { title: '', content: '', inputDesc: '', outputDesc: '', difficulty: 'EASY', status: 1 })
  languagesText.value = 'java,python,cpp'
  knowledgeText.value = 'array'
  editVisible.value = true
}

const openEdit = async (id: number) => {
  const d: any = await api.adminProblemDetail(id)
  editingId.value = id
  Object.assign(form, d)
  languagesText.value = (d.supportLanguages || []).join(',')
  knowledgeText.value = (d.knowledgePoints || []).join(',')
  editVisible.value = true
}

const save = async () => {
  const payload = {
    ...form,
    supportLanguages: languagesText.value.split(',').map((s) => s.trim()).filter(Boolean),
    knowledgePoints: knowledgeText.value.split(',').map((s) => s.trim()).filter(Boolean)
  }
  if (editingId.value) await api.adminUpdateProblem(editingId.value, payload)
  else await api.adminCreateProblem(payload)
  ElMessage.success('保存成功')
  editVisible.value = false
  await load()
}

const remove = async (id: number) => {
  await api.adminDeleteProblem(id)
  ElMessage.success('删除成功')
  await load()
}

const openTestcases = async (id: number) => {
  const d: any = await api.adminProblemDetail(id)
  testcaseProblemId.value = id
  testcaseText.value = JSON.stringify((d.testcases || []).map((x: any) => ({ inputData: x.inputData, expectedOutput: x.expectedOutput, caseOrder: x.caseOrder })), null, 2)
  testcaseVisible.value = true
}

const saveTestcases = async () => {
  if (!testcaseProblemId.value) return
  let testcases: any[] = []
  try {
    testcases = JSON.parse(testcaseText.value)
  } catch {
    ElMessage.error('测试用例 JSON 格式不正确')
    return
  }
  await api.adminReplaceTestcases(testcaseProblemId.value, { testcases })
  ElMessage.success('测试用例已更新')
  testcaseVisible.value = false
}

onMounted(load)
</script>

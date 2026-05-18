<template>
  <el-row :gutter="12">
    <el-col :span="14">
      <el-card>
        <h3>{{ detail?.title }}</h3>
        <p><b>难度:</b> {{ detail?.difficulty }}</p>
        <p style="white-space: pre-wrap">{{ detail?.content }}</p>
      </el-card>
    </el-col>
    <el-col :span="10">
      <el-card>
        <el-form>
          <el-form-item label="语言">
            <el-select v-model="lang"><el-option label="java" value="java" /></el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model="code" type="textarea" :rows="12" />
          </el-form-item>
          <el-space>
            <el-button @click="run">运行测试</el-button>
            <el-button type="primary" @click="submit">正式提交</el-button>
          </el-space>
        </el-form>
        <p style="white-space: pre-wrap">{{ result }}</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '@/api'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)
const detail = ref<any>(null)
const lang = ref('java')
const code = ref('public class Solution { public static void main(String[] args){} }')
const result = ref('')

const zh = (status: string) => {
  const m: Record<string, string> = {
    PENDING: '待判题',
    JUDGING: '判题中',
    ACCEPTED: '通过',
    WRONG_ANSWER: '答案错误',
    COMPILE_ERROR: '编译错误',
    RUNTIME_ERROR: '运行错误',
    TIME_LIMIT_EXCEEDED: '超时',
    MEMORY_LIMIT_EXCEEDED: '超内存',
    SYSTEM_ERROR: '系统错误',
    SUCCESS: '运行成功'
  }
  return m[status] || status
}

const load = async () => { detail.value = await api.problemDetail(id) }
const run = async () => {
  const r: any = await api.runTest({ problemId: id, language: lang.value, codeContent: code.value, customInput: '' })
  result.value = `状态: ${zh(r.status)}\n耗时(ms): ${r.timeMs ?? '-'}\n输出:\n${r.output ?? ''}\n错误:\n${r.errorMessage ?? ''}`
}
const submit = async () => {
  const r: any = await api.createSubmission({ problemId: id, language: lang.value, codeContent: code.value, source: 'submit' })
  result.value = `提交成功\n提交ID: ${r.submissionId}\n状态: ${zh(r.status)}`
  router.push('/submissions')
}

onMounted(load)
</script>

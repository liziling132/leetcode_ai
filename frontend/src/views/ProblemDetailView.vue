<template>
  <el-row :gutter="12">
    <el-col :span="14">
      <el-card class="problem-card">
        <div class="problem-head">
          <h2>{{ detail?.title || '-' }}</h2>
          <el-tag>{{ detail?.difficulty || '-' }}</el-tag>
        </div>

        <section class="block">
          <h4>题目描述</h4>
          <p class="block-text">{{ detail?.content || '暂无描述' }}</p>
        </section>

        <section class="block">
          <h4>输入说明</h4>
          <p class="block-text">{{ detail?.inputDesc || '暂无输入说明' }}</p>
        </section>

        <section class="block">
          <h4>输出说明</h4>
          <p class="block-text">{{ detail?.outputDesc || '暂无输出说明' }}</p>
        </section>

        <section class="block" v-if="examples.length">
          <h4>示例</h4>
          <div v-for="(ex, idx) in examples" :key="idx" class="example-box">
            <div class="example-title">示例 {{ idx + 1 }}</div>
            <pre>{{ ex }}</pre>
          </div>
        </section>
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
import { computed, onMounted, ref } from 'vue'
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

const examples = computed(() => {
  const txt = `${detail.value?.inputDesc || ''}\n${detail.value?.outputDesc || ''}`
  const rows = txt.split('\n').map((s: string) => s.trim()).filter(Boolean)
  return rows.filter((s: string) => s.includes('示例') || s.includes('example') || s.includes('nums=') || s.includes('target='))
})

const load = async () => {
  detail.value = await api.problemDetail(id)
}

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

<style scoped>
.problem-card {
  border-radius: 12px;
}

.problem-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.problem-head h2 {
  margin: 0;
  font-size: 30px;
  color: #1d2d55;
}

.block {
  margin-top: 14px;
  padding: 12px;
  border: 1px solid #e6ebf5;
  border-radius: 10px;
  background: #fbfdff;
}

.block h4 {
  margin: 0 0 8px 0;
  color: #233b72;
}

.block-text {
  margin: 0;
  white-space: pre-wrap;
  line-height: 1.8;
  color: #2d3f64;
}

.example-box {
  border-radius: 8px;
  border: 1px solid #e9edf6;
  background: #fff;
  margin-top: 8px;
}

.example-title {
  font-size: 13px;
  color: #5e6f8f;
  border-bottom: 1px dashed #e8edf6;
  padding: 8px 10px;
}

.example-box pre {
  margin: 0;
  padding: 10px;
  white-space: pre-wrap;
  line-height: 1.6;
  color: #2d3f64;
}
</style>

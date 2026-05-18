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

const load = async () => { detail.value = await api.problemDetail(id) }
const run = async () => { const r: any = await api.runTest({ problemId: id, language: lang.value, codeContent: code.value, customInput: '' }); result.value = JSON.stringify(r, null, 2) }
const submit = async () => {
  const r: any = await api.createSubmission({ problemId: id, language: lang.value, codeContent: code.value, source: 'submit' })
  result.value = JSON.stringify(r, null, 2)
  router.push('/submissions')
}

onMounted(load)
</script>

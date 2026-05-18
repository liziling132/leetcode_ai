<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="id" label="提交ID" width="110" />
      <el-table-column prop="problemTitle" label="题目" />
      <el-table-column prop="judgeStatus" label="状态" width="160" />
      <el-table-column label="AI解释" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="explain(scope.row.id)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-input v-model="aiText" type="textarea" :rows="8" style="margin-top:12px" />
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { api } from '@/api'

const list = ref<any[]>([])
const aiText = ref('')
const load = async () => { const data: any = await api.submissions({ page: 1, size: 20 }); list.value = data.list || [] }
const explain = async (id: number) => { const data: any = await api.explain(id); aiText.value = data.explanation }
onMounted(load)
</script>

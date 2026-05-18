<template>
  <el-card>
    <el-button type="primary" :loading="loading" @click="refresh">
      {{ loading ? 'AI智能分析中...' : '生成推荐' }}
    </el-button>

    <el-skeleton :loading="loading" animated :rows="4" style="margin-top: 12px">
      <template #default>
        <el-alert
          v-if="!touched"
          title="点击“生成推荐”后再生成结果。"
          type="info"
          show-icon
          :closable="false"
          style="margin-top: 12px"
        />
        <el-alert
          v-if="touched && !loading && list.length === 0"
          title="暂无推荐数据：请先做题并产生错题记录后再生成推荐。"
          type="info"
          show-icon
          :closable="false"
          style="margin-top: 12px"
        />
        <el-table :data="list" style="margin-top: 12px">
          <el-table-column prop="problemId" label="题目ID" width="100" />
          <el-table-column prop="title" label="题目" min-width="220" />
          <el-table-column prop="difficulty" label="难度" width="120" />
          <el-table-column prop="reason" label="推荐理由" min-width="320" />
          <el-table-column label="学习资源" min-width="260">
            <template #default="scope">
              <template v-if="scope.row.resourceUrl">
                <a :href="scope.row.resourceUrl" target="_blank" rel="noopener noreferrer">
                  {{ scope.row.resourceTitle || scope.row.resourceUrl }}
                </a>
                <el-tag size="small" style="margin-left: 8px">{{ scope.row.resourceType || 'resource' }}</el-tag>
              </template>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column prop="score" label="分数" width="100" />
        </el-table>
      </template>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const list = ref<any[]>([])
const loading = ref(false)
const touched = ref(false)

const refresh = async () => {
  touched.value = true
  loading.value = true
  try {
    const data: any = await api.recommendations({ size: 5 })
    list.value = data.list || []
  } catch (e: any) {
    ElMessage.error(e?.message || '生成推荐失败')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <el-card>
    <el-form inline @submit.prevent>
      <el-form-item label="关键字"><el-input v-model="q.keyword" clearable /></el-form-item>
      <el-form-item label="难度">
        <el-select v-model="q.difficulty" clearable style="width: 140px">
          <el-option label="EASY" value="EASY" />
          <el-option label="MEDIUM" value="MEDIUM" />
          <el-option label="HARD" value="HARD" />
        </el-select>
      </el-form-item>
      <el-form-item label="语言">
        <el-select v-model="q.language" clearable style="width: 140px">
          <el-option label="java" value="java" />
          <el-option label="python" value="python" />
          <el-option label="cpp" value="cpp" />
        </el-select>
      </el-form-item>
      <el-form-item label="标签ID"><el-input-number v-model="q.tagId" :min="1" controls-position="right" /></el-form-item>
      <el-button type="primary" @click="onSearch">查询</el-button>
    </el-form>

    <el-table :data="list" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="题目" />
      <el-table-column prop="difficulty" label="难度" width="120" />
      <el-table-column prop="tags" label="标签" width="220">
        <template #default="scope">{{ (scope.row.tags || []).join(' / ') }}</template>
      </el-table-column>
      <el-table-column label="收藏" width="100">
        <template #default="scope">
          <el-button link :type="isFav(scope.row.id) ? 'warning' : 'primary'" @click="toggleFav(scope.row.id)">
            {{ isFav(scope.row.id) ? '已收藏' : '收藏' }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" @click="$router.push(`/problems/${scope.row.id}`)">进入</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 12px; display: flex; justify-content: flex-end;">
      <el-pagination
        v-model:current-page="q.page"
        v-model:page-size="q.size"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50]"
        :total="total"
        @current-change="load"
        @size-change="onSizeChange"
      />
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const q = reactive<any>({ page: 1, size: 20, keyword: '', difficulty: '', language: '', tagId: null })
const list = ref<any[]>([])
const total = ref(0)
const favSet = ref<Set<number>>(new Set())

const loadFavorites = async () => {
  const data: any = await api.favorites({ page: 1, size: 500 })
  favSet.value = new Set((data.list || []).map((x: any) => Number(x.problemId)))
}

const load = async () => {
  const data: any = await api.problems(q)
  list.value = data.list || []
  total.value = data.total || 0
}

const onSearch = async () => {
  q.page = 1
  await load()
}

const onSizeChange = async () => {
  q.page = 1
  await load()
}

const isFav = (id: number) => favSet.value.has(Number(id))

const toggleFav = async (id: number) => {
  try {
    if (isFav(id)) {
      await api.unfavorite(id)
      favSet.value.delete(Number(id))
      ElMessage.success('已取消收藏')
    } else {
      await api.favorite(id)
      favSet.value.add(Number(id))
      ElMessage.success('收藏成功')
    }
  } catch (e: any) {
    ElMessage.error(e?.message || '操作失败')
  }
}

onMounted(async () => {
  await Promise.all([load(), loadFavorites()])
})
</script>

<template>
  <el-container class="layout">
    <el-aside width="240px" class="sidebar">
      <h2>易题通</h2>
      <el-menu :default-active="$route.path" router>
        <el-menu-item index="/problems">题库</el-menu-item>
        <el-menu-item index="/submissions">提交记录</el-menu-item>
        <el-menu-item index="/wrong-questions">错题本</el-menu-item>
        <el-menu-item index="/recommendations">推荐</el-menu-item>
        <el-menu-item index="/stats">学习数据</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>

        <el-sub-menu v-if="role === 'ADMIN'" index="admin">
          <template #title>管理后台</template>
          <el-menu-item index="/admin/monitor">监控总览</el-menu-item>
          <el-menu-item index="/admin/users">用户管理</el-menu-item>
          <el-menu-item index="/admin/problems">题目管理</el-menu-item>
          <el-menu-item index="/admin/tags">标签管理</el-menu-item>
          <el-menu-item index="/admin/resources">资源管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div>易题通 MVP 前端</div>
        <div>
          <el-tag v-if="token" :type="role === 'ADMIN' ? 'danger' : 'info'" style="margin-right: 8px">{{ role }}</el-tag>
          <el-button v-if="!token" type="primary" @click="$router.push('/login')">登录</el-button>
          <el-button v-else @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
const token = computed(() => auth.token)
const role = computed(() => auth.role)

const logout = () => {
  auth.logout()
  location.href = '/login'
}
</script>

<style scoped>
.layout { min-height: 100vh; }
.sidebar { background: #f6f7fb; padding: 12px; }
.header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; }
</style>

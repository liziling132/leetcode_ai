<template>
  <div class="app-shell">
    <el-container class="layout">
      <el-aside width="252px" class="sidebar">
        <div class="brand">
          <h2>易题通</h2>
          <p>Smart Practice Studio</p>
        </div>
        <el-menu :default-active="$route.path" router>
          <el-menu-item index="/problems">题库</el-menu-item>
          <el-menu-item index="/submissions">提交记录</el-menu-item>
          <el-menu-item index="/wrong-questions">错题本</el-menu-item>
          <el-menu-item index="/recommendations">推荐</el-menu-item>
          <el-menu-item index="/recommendation-records">推荐记录</el-menu-item>
          <el-menu-item index="/favorites">我的收藏</el-menu-item>
          <el-menu-item index="/stats">学习数据</el-menu-item>
          <el-menu-item index="/profile">个人中心</el-menu-item>

          <el-sub-menu v-if="role === 'ADMIN'" index="admin">
            <template #title>管理后台</template>
            <el-menu-item index="/admin/monitor">监控总览</el-menu-item>
            <el-menu-item index="/admin/users">用户管理</el-menu-item>
            <el-menu-item index="/admin/problems">题目管理</el-menu-item>
            <el-menu-item index="/admin/tags">标签管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div class="header-title">
            <h3>学习控制台</h3>
            <span>聚焦题解与反馈闭环</span>
          </div>
          <div class="header-actions">
            <el-avatar v-if="token" :size="34" :src="avatarUrl || undefined" class="user-avatar">
              {{ role === 'ADMIN' ? 'A' : 'U' }}
            </el-avatar>
            <el-tag v-if="token" :type="role === 'ADMIN' ? 'danger' : 'info'" effect="dark">{{ role }}</el-tag>
            <el-button v-if="!token" type="primary" @click="$router.push('/login')">登录</el-button>
            <el-button v-else @click="logout">退出</el-button>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, watch } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { api } from '@/api'

const auth = useAuthStore()
const token = computed(() => auth.token)
const role = computed(() => auth.role)
const avatarUrl = computed(() => auth.avatarUrl)

const loadProfileAvatar = async () => {
  if (!auth.token) return
  try {
    const data: any = await api.profile()
    auth.setAvatarUrl(data.avatarUrl || '')
  } catch {
    auth.setAvatarUrl('')
  }
}

const logout = () => {
  auth.logout()
  location.href = '/login'
}

watch(
  () => auth.token,
  () => {
    loadProfileAvatar()
  }
)

onMounted(loadProfileAvatar)
</script>

<style scoped>
.app-shell {
  min-height: 100vh;
  background: radial-gradient(circle at 15% 15%, #e3f0ff 0, #f8fbff 45%, #f5f7fa 100%);
  padding: 16px;
}

.layout {
  min-height: calc(100vh - 32px);
  border-radius: 18px;
  overflow: hidden;
  box-shadow: 0 18px 40px rgba(22, 34, 65, 0.08);
  animation: fade-up 0.45s ease-out;
}

.sidebar {
  background: linear-gradient(180deg, #f7faff 0%, #f2f6fd 100%);
  padding: 14px 10px;
  border-right: 1px solid #e4ebf7;
}

.brand {
  padding: 10px 14px 16px;
}

.brand h2 {
  margin: 0;
  color: #152347;
  font-size: 32px;
  letter-spacing: 0.5px;
}

.brand p {
  margin: 6px 0 0;
  color: #5d6b85;
  font-size: 12px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e9eef9;
  padding: 0 10px;
  background: rgba(255, 255, 255, 0.75);
  backdrop-filter: blur(8px);
}

.header-title h3 {
  margin: 0;
  font-size: 20px;
  color: #1a2a52;
}

.header-title span {
  color: #6f7e97;
  font-size: 12px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-avatar {
  border: 1px solid #d6e2f7;
}

@keyframes fade-up {
  from {
    opacity: 0;
    transform: translateY(8px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>

<template>
  <el-row :gutter="12">
    <el-col :span="12">
      <el-card>
        <template #header>个人资料</template>
        <el-form :model="profileForm" label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="profileForm.username" />
          </el-form-item>
          <el-form-item label="头像URL">
            <el-input v-model="profileForm.avatarUrl" />
          </el-form-item>
          <el-form-item label="简介">
            <el-input v-model="profileForm.bio" type="textarea" :rows="4" />
          </el-form-item>
          <el-button type="primary" @click="saveProfile">保存资料</el-button>
        </el-form>
      </el-card>
    </el-col>

    <el-col :span="12">
      <el-card>
        <template #header>修改密码</template>
        <el-form :model="pwdForm" label-width="100px">
          <el-form-item label="旧密码">
            <el-input v-model="pwdForm.oldPassword" type="password" show-password />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="pwdForm.newPassword" type="password" show-password />
          </el-form-item>
          <el-button type="warning" @click="savePassword">修改密码</el-button>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'

const profileForm = reactive({
  username: '',
  avatarUrl: '',
  bio: ''
})

const pwdForm = reactive({
  oldPassword: '',
  newPassword: ''
})

const load = async () => {
  const data: any = await api.profile()
  profileForm.username = data.username || ''
  profileForm.avatarUrl = data.avatarUrl || ''
  profileForm.bio = data.bio || ''
}

const saveProfile = async () => {
  await api.updateProfile({
    username: profileForm.username,
    avatarUrl: profileForm.avatarUrl,
    bio: profileForm.bio
  })
  ElMessage.success('资料已保存')
}

const savePassword = async () => {
  if (!pwdForm.oldPassword || !pwdForm.newPassword) {
    ElMessage.error('请完整填写旧密码和新密码')
    return
  }
  await api.updatePassword({ ...pwdForm })
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
  ElMessage.success('密码已修改')
}

onMounted(load)
</script>

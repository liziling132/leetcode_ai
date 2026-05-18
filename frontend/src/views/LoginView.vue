<template>
  <el-card class="w">
    <el-form ref="formRef" :model="form" :rules="rules" @submit.prevent>
      <el-form-item>
        <el-radio-group v-model="isAdmin">
          <el-radio :value="false">普通用户</el-radio>
          <el-radio :value="true">管理员</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="用户名" prop="username"><el-input v-model="form.username" /></el-form-item>
      <el-form-item label="密码" prop="password"><el-input v-model="form.password" type="password" show-password /></el-form-item>
      <el-space>
        <el-button type="primary" @click="onLogin">登录</el-button>
        <el-button v-if="!isAdmin" @click="onRegister">注册</el-button>
      </el-space>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { api } from '@/api'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const form = reactive({ username: '', password: '' })
const isAdmin = ref(false)
const formRef = ref<FormInstance>()
const auth = useAuthStore()
const router = useRouter()

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 4, max: 32, message: '用户名长度 4-32', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母数字下划线', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 64, message: '密码长度 6-64', trigger: 'blur' }
  ]
}

const onLogin = async () => {
  try {
    const valid = await formRef.value?.validate()
    if (!valid) return
    const data: any = isAdmin.value ? await api.adminLogin(form) : await api.login(form)
    auth.setToken(data.token)
    ElMessage.success('登录成功')
    router.push(auth.role === 'ADMIN' ? '/admin/monitor' : '/problems')
  } catch (e: any) {
    ElMessage.error(e?.message || '登录失败')
  }
}

const onRegister = async () => {
  try {
    const valid = await formRef.value?.validate()
    if (!valid) return
    const data: any = await api.register({ ...form, nickname: form.username })
    auth.setToken(data.token)
    ElMessage.success('注册成功')
    router.push('/problems')
  } catch (e: any) {
    ElMessage.error(e?.message || '注册失败')
  }
}
</script>

<style scoped>
.w { max-width: 420px; margin: 120px auto; }
</style>

<template>
  <div class="auth-page">
    <div class="auth-card">
      <div class="auth-logo">📇</div>
      <h2>欢迎回来</h2>
      <p class="auth-subtitle">登录你的通讯录管理系统</p>

      <div v-if="errorMsg" class="alert alert-error">{{ errorMsg }}</div>
      <div v-if="successMsg" class="alert alert-success">{{ successMsg }}</div>

      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>用户名</label>
          <input v-model="username" type="text" placeholder="请输入用户名" required>
        </div>
        <div class="form-group">
          <label>密码</label>
          <input v-model="password" type="password" placeholder="请输入密码" required>
        </div>
        <div class="btn-group">
          <button type="submit" class="btn btn-primary">登 录</button>
          <button type="button" class="btn btn-secondary" @click="$router.push('/register')">注 册</button>
        </div>
      </form>

      <div class="test-accounts">
        <p>测试账号</p>
        <p><code>admin / 123456</code></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { login } from '../api/index.js'

const router = useRouter()
const route = useRoute()

const username = ref('')
const password = ref('')
const errorMsg = ref('')
const successMsg = ref(route.query.registered ? '注册成功，请登录！' : '')

async function handleLogin() {
  errorMsg.value = ''
  try {
    const res = await login(username.value, password.value)
    if (res.data.code === 200) {
      sessionStorage.setItem('user', JSON.stringify(res.data.data))
      router.push('/contacts')
    } else {
      errorMsg.value = res.data.message
    }
  } catch {
    errorMsg.value = '登录失败，请检查网络连接'
  }
}
</script>

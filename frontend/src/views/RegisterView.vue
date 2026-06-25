<template>
  <div class="auth-page">
    <div class="auth-card">
      <div class="auth-logo">✍️</div>
      <h2>创建账号</h2>
      <p class="auth-subtitle">注册后即可开始管理你的通讯录</p>

      <div v-if="errorMsg" class="alert alert-error">{{ errorMsg }}</div>

      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label>用户名</label>
          <input v-model="form.username" type="text" placeholder="设置你的用户名" required>
          <small>用户名必须唯一</small>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>密码</label>
            <input v-model="form.password" type="password" placeholder="设置密码" required>
          </div>
          <div class="form-group">
            <label>确认密码</label>
            <input v-model="form.password2" type="password" placeholder="再次输入密码" required>
          </div>
        </div>
        <div class="form-group">
          <label>年龄</label>
          <input v-model="form.age" type="text" placeholder="请输入年龄数字" required>
          <small>必须是数字</small>
        </div>
        <div class="btn-group">
          <button type="submit" class="btn btn-primary">注 册</button>
          <button type="button" class="btn btn-secondary" @click="$router.push('/')">返回登录</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api/index.js'

const router = useRouter()
const errorMsg = ref('')
const form = reactive({
  username: '',
  password: '',
  password2: '',
  age: '',
})

async function handleRegister() {
  errorMsg.value = ''
  const { username, password, password2, age } = form

  if (password !== password2) {
    errorMsg.value = '两次密码不一致，请重新输入！'
    return
  }
  const ageNum = parseInt(age)
  if (isNaN(ageNum) || ageNum < 1 || ageNum > 120) {
    errorMsg.value = '年龄输入不合法！'
    return
  }

  try {
    const res = await register(username, password, password2, ageNum)
    if (res.data.code === 200) {
      router.push('/?registered=true')
    } else {
      errorMsg.value = res.data.message
    }
  } catch {
    errorMsg.value = '注册失败，请检查网络连接'
  }
}
</script>

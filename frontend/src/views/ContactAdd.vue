<template>
  <div>
    <nav class="navbar">
      <div class="navbar-brand">
        <div class="brand-icon">📇</div>
        <span>通讯录管理系统</span>
      </div>
      <div class="navbar-user">
        <span class="username">{{ user?.username }}</span>
        <div class="avatar">{{ user?.username?.charAt(0) }}</div>
        <button class="btn btn-primary btn-sm" @click="$router.push('/contacts')">返回</button>
      </div>
    </nav>

    <div class="page-content">
      <div class="container">
        <div class="form-page">
          <button class="back-link" @click="$router.push('/contacts')">← 返回通讯录</button>
          <div class="form-card">
            <h2>添加联系人</h2>
            <p class="form-subtitle">填写以下信息添加新的联系人</p>
            <div v-if="errorMsg" class="alert alert-error">{{ errorMsg }}</div>

            <form @submit.prevent="handleAdd">
              <div class="form-group">
                <label>姓名</label>
                <input v-model="form.name" type="text" placeholder="请输入联系人姓名" required>
              </div>
              <div class="form-row">
                <div class="form-group">
                  <label>性别</label>
                  <select v-model="form.gender" required>
                    <option value="">请选择</option>
                    <option value="男">男</option>
                    <option value="女">女</option>
                    <option value="其他">其他</option>
                  </select>
                </div>
                <div class="form-group">
                  <label>电话</label>
                  <input v-model="form.phone" type="text" placeholder="请输入手机号码" required>
                  <small>同一用户不能有重复电话</small>
                </div>
              </div>
              <div class="form-group">
                <label>邮箱地址</label>
                <input v-model="form.email" type="email" placeholder="请输入邮箱（可选）">
              </div>
              <div class="form-group">
                <label>通讯地址</label>
                <textarea v-model="form.address" placeholder="请输入通讯地址（可选）"></textarea>
              </div>
              <div class="btn-group">
                <button type="submit" class="btn btn-primary">✓ 保存</button>
                <button type="button" class="btn btn-secondary" @click="$router.push('/contacts')">取消</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { addContact } from '../api/index.js'

const router = useRouter()
const errorMsg = ref('')
const user = ref(JSON.parse(sessionStorage.getItem('user') || '{}'))
const form = reactive({
  name: '', gender: '', phone: '', email: '', address: '',
})

async function handleAdd() {
  errorMsg.value = ''
  if (!form.name.trim()) { errorMsg.value = '姓名不能为空！'; return }
  if (!form.gender) { errorMsg.value = '请选择性别！'; return }
  if (!/^1[3-9]\d{9}$/.test(form.phone)) { errorMsg.value = '请输入有效的手机号码！'; return }

  try {
    const res = await addContact(form)
    if (res.data.code === 200) {
      router.push('/contacts')
    } else {
      errorMsg.value = res.data.message
    }
  } catch {
    errorMsg.value = '添加失败'
  }
}
</script>

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
        <div class="form-page" v-if="contact">
          <button class="back-link" @click="$router.push('/contacts')">← 返回通讯录</button>
          <div class="form-card">
            <h2>编辑联系人</h2>
            <p class="form-subtitle">修改联系人的信息</p>
            <div v-if="errorMsg" class="alert alert-error">{{ errorMsg }}</div>

            <form @submit.prevent="handleEdit">
              <div class="form-group">
                <label>编号</label>
                <input :value="contact.id" readonly>
                <small>编号自动生成，不可修改</small>
              </div>
              <div class="form-row">
                <div class="form-group">
                  <label>姓名</label>
                  <input v-model="form.name" type="text" required>
                </div>
                <div class="form-group">
                  <label>性别</label>
                  <select v-model="form.gender" required>
                    <option value="男">男</option>
                    <option value="女">女</option>
                    <option value="其他">其他</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label>电话</label>
                <input v-model="form.phone" type="text" required>
              </div>
              <div class="form-group">
                <label>邮箱地址</label>
                <input v-model="form.email" type="email" placeholder="（可选）">
              </div>
              <div class="form-group">
                <label>通讯地址</label>
                <textarea v-model="form.address" placeholder="（可选）"></textarea>
              </div>
              <div class="btn-group">
                <button type="submit" class="btn btn-primary">✓ 保存修改</button>
                <button type="button" class="btn btn-secondary" @click="$router.push('/contacts')">取消</button>
              </div>
            </form>
          </div>
        </div>
        <div v-else class="empty-state">
          <h3>加载中...</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getContact, updateContact } from '../api/index.js'

const route = useRoute()
const router = useRouter()
const errorMsg = ref('')
const user = ref(JSON.parse(sessionStorage.getItem('user') || '{}'))
const contact = ref(null)
const form = reactive({
  name: '', gender: '男', phone: '', email: '', address: '',
})

onMounted(async () => {
  try {
    const res = await getContact(route.params.id)
    if (res.data.code === 200) {
      contact.value = res.data.data
      form.name = res.data.data.name
      form.gender = res.data.data.gender
      form.phone = res.data.data.phone
      form.email = res.data.data.email || ''
      form.address = res.data.data.address || ''
    } else {
      router.push('/contacts')
    }
  } catch {
    router.push('/contacts')
  }
})

async function handleEdit() {
  errorMsg.value = ''
  try {
    const res = await updateContact(route.params.id, form)
    if (res.data.code === 200) {
      router.push('/contacts')
    } else {
      errorMsg.value = res.data.message
    }
  } catch {
    errorMsg.value = '修改失败'
  }
}
</script>

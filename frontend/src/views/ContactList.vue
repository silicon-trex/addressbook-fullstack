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
        <button class="btn btn-primary btn-sm" @click="handleLogout">退出</button>
      </div>
    </nav>

    <div class="page-content">
      <div class="container">
        <div v-if="successMsg" class="alert alert-success">{{ successMsg }}</div>
        <div v-if="errorMsg" class="alert alert-error">{{ errorMsg }}</div>

        <div class="page-header">
          <div class="page-title">
            通讯录
            <small>共 {{ contacts.length }} 位联系人</small>
          </div>
          <div class="toolbar">
            <div class="search-box">
              <span class="search-icon">🔍</span>
              <input v-model="keyword" type="text" placeholder="搜索姓名、电话或邮箱..." @keyup.enter="search">
            </div>
            <button class="btn btn-primary" @click="$router.push('/contacts/add')">+ 添加联系人</button>
          </div>
        </div>

        <div v-if="searchKeyword" class="search-info">
          <h3 v-if="contacts.length === 0">
            未找到包含 "<span>{{ searchKeyword }}</span>" 的记录
          </h3>
          <h3 v-else>
            搜索 "<span>{{ searchKeyword }}</span>" 的结果（共 {{ contacts.length }} 条记录）
          </h3>
          <a href="javascript:void(0)" @click="clearSearch">显示全部记录 →</a>
        </div>

        <div v-if="contacts.length === 0" class="empty-state">
          <div class="empty-icon">📭</div>
          <h3>暂无联系人记录</h3>
          <p>点击下方按钮开始添加你的第一位联系人</p>
          <button class="btn btn-primary" @click="$router.push('/contacts/add')">+ 添加联系人</button>
        </div>

        <div v-else class="table-wrapper">
          <table class="data-table">
            <thead>
              <tr>
                <th>#</th>
                <th>姓名</th>
                <th>性别</th>
                <th>电话</th>
                <th>邮箱地址</th>
                <th>通讯地址</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(contact, index) in contacts" :key="contact.id">
                <td class="row-number">{{ index + 1 }}</td>
                <td><strong>{{ contact.name }}</strong></td>
                <td>
                  <span class="gender-badge"
                    :class="contact.gender === '男' ? 'male' : (contact.gender === '女' ? 'female' : 'other')">
                    {{ contact.gender }}
                  </span>
                </td>
                <td>{{ contact.phone }}</td>
                <td :class="{ 'cell-muted': !contact.email }">{{ contact.email || '未填写' }}</td>
                <td :class="{ 'cell-muted': !contact.address }">{{ contact.address || '未填写' }}</td>
                <td>
                  <div class="action-cell">
                    <button class="action-btn edit" @click="$router.push(`/contacts/edit/${contact.id}`)">✎ 编辑</button>
                    <button class="action-btn delete" @click="confirmDelete(contact.id)">🗑 删除</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getContacts, deleteContact, logout as apiLogout } from '../api/index.js'

const router = useRouter()
const contacts = ref([])
const keyword = ref('')
const searchKeyword = ref('')
const successMsg = ref('')
const errorMsg = ref('')
const user = ref(JSON.parse(sessionStorage.getItem('user') || '{}'))

async function fetchContacts(kw) {
  try {
    const res = await getContacts(kw || '')
    if (res.data.code === 200) {
      contacts.value = res.data.data
    }
  } catch {
    errorMsg.value = '获取联系人失败'
  }
}

function search() {
  searchKeyword.value = keyword.value
  fetchContacts(keyword.value)
}

function clearSearch() {
  keyword.value = ''
  searchKeyword.value = ''
  fetchContacts()
}

function confirmDelete(id) {
  if (confirm('确定要删除这条联系人记录吗？')) {
    doDelete(id)
  }
}

async function doDelete(id) {
  try {
    const res = await deleteContact(id)
    if (res.data.code === 200) {
      successMsg.value = '删除成功！'
      fetchContacts(searchKeyword.value)
    } else {
      errorMsg.value = res.data.message
    }
  } catch {
    errorMsg.value = '删除失败'
  }
}

async function handleLogout() {
  await apiLogout()
  sessionStorage.removeItem('user')
  router.push('/')
}

onMounted(() => {
  fetchContacts()
})
</script>

import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
  headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
})

// 将对象转为表单格式
function toForm(data) {
  const params = new URLSearchParams()
  Object.entries(data).forEach(([k, v]) => {
    if (v !== undefined && v !== null) params.append(k, v)
  })
  return params
}

// 登录
export function login(username, password) {
  return api.post('/login', toForm({ username, password }))
}

// 注册
export function register(username, password, password2, age) {
  return api.post('/register', toForm({ username, password, password2, age }))
}

// 退出
export function logout() {
  return api.get('/logout')
}

// 获取当前用户
export function getMe() {
  return api.get('/me')
}

// 获取联系人列表
export function getContacts(keyword) {
  return api.get('/contacts', { params: keyword ? { keyword } : {} })
}

// 获取单个联系人
export function getContact(id) {
  return api.get(`/contacts/${id}`)
}

// 添加联系人
export function addContact(data) {
  return api.post('/contacts', toForm(data))
}

// 编辑联系人
export function updateContact(id, data) {
  return api.put(`/contacts/${id}`, toForm(data))
}

// 删除联系人
export function deleteContact(id) {
  return api.delete(`/contacts/${id}`)
}

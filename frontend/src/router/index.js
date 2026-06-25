import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import ContactList from '../views/ContactList.vue'
import ContactAdd from '../views/ContactAdd.vue'
import ContactEdit from '../views/ContactEdit.vue'

const routes = [
  { path: '/', name: 'login', component: LoginView },
  { path: '/register', name: 'register', component: RegisterView },
  { path: '/contacts', name: 'contacts', component: ContactList },
  { path: '/contacts/add', name: 'addContact', component: ContactAdd },
  { path: '/contacts/edit/:id', name: 'editContact', component: ContactEdit },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由守卫 — 未登录跳转登录页
router.beforeEach((to, from, next) => {
  const user = sessionStorage.getItem('user')
  if (to.name !== 'login' && to.name !== 'register' && !user) {
    next({ name: 'login' })
  } else {
    next()
  }
})

export default router

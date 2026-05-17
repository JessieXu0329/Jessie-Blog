import { createRouter, createWebHistory } from 'vue-router'
import { useAdminAuth } from '../composables/useAdminAuth'

const routes = [
  // 首页重定向到 About
  { path: '/',        redirect: '/about' },

  // 主要页面
  { path: '/about',   name: 'About',   component: () => import('../views/AboutView.vue') },
  { path: '/tech',    name: 'Tech',    component: () => import('../views/TechView.vue') },
  { path: '/essays',  name: 'Essays',  component: () => import('../views/EssayView.vue') },
  { path: '/sports',  name: 'Sports',  component: () => import('../views/SportsView.vue') },
  { path: '/media',   name: 'Media',   component: () => import('../views/MediaView.vue') },

  // 文章详情
  { path: '/posts/:id', name: 'PostDetail', component: () => import('../views/PostDetailView.vue') },

  // TECH 项目详情
  { path: '/projects/:id', name: 'ProjectDetail', component: () => import('../views/TechProjectDetailView.vue') },

  // 后台
  { path: '/admin/login', name: 'AdminLogin', component: () => import('../views/AdminLoginView.vue') },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/AdminView.vue'),
    meta: { requiresAuth: true },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0, behavior: 'smooth' }),
})

router.beforeEach((to) => {
  if (to.meta.requiresAuth) {
    const { isAuthed } = useAdminAuth()
    if (!isAuthed()) return { name: 'AdminLogin' }
  }
})

export default router

import axios from 'axios'

// VITE_API_BASE 在 Vercel 环境变量里设置，例如 https://你的后端.onrender.com/api
// 本地开发用 Vite proxy，不需要设
const baseURL = import.meta.env.VITE_API_BASE || '/api'

const http = axios.create({
  baseURL,
  timeout: 10_000,
  headers: { 'Content-Type': 'application/json' },
})

http.interceptors.response.use(
  (res) => res.data,
  (err) => {
    const status = err.response?.status
    const message = err.response?.data?.detail ?? err.message
    console.error(`[API] ${status ?? 'Network'}: ${message}`)
    return Promise.reject(err)
  },
)

export default http

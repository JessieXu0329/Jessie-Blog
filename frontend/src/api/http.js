import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
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

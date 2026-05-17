import axios from 'axios'

const base = import.meta.env.VITE_API_BASE || '/api'

const http = axios.create({
  baseURL: `${base}/admin`,
  timeout: 30_000,
})

http.interceptors.response.use(
  (res) => res.data,
  (err) => {
    const status = err.response?.status
    const message = err.response?.data?.detail ?? err.message
    console.error(`[Admin API] ${status ?? 'Network'}: ${message}`)
    return Promise.reject(err)
  },
)

/**
 * 通用上传：发送 FormData（file + JSON data）到 /api/admin/upload/{section}
 * @param {string}   section  — "tech" | "sports" | "media"
 * @param {File}     file     — 图片文件
 * @param {object}   fields   — 业务字段
 * @param {string}   token    — X-Admin-Token
 */
export function adminUpload(section, file, fields, token) {
  const form = new FormData()
  form.append('file', file)
  form.append('data', new Blob([JSON.stringify(fields)], { type: 'application/json' }))

  // 不要手动设 Content-Type，让浏览器自动带 boundary
  return http.post(`/upload/${section}`, form, {
    headers: {
      'X-Admin-Token': token,
    },
  })
}

/**
 * 删除条目 DELETE /api/admin/{section}/{id}
 */
export function adminDelete(section, id, token) {
  return http.delete(`/${section}/${id}`, {
    headers: { 'X-Admin-Token': token },
  })
}

/** 获取 TECH 项目列表 */
export function fetchTechProjects()    { return http.get('/tech') }
/** 获取单个 TECH 项目 */
export function fetchTechProject(id)   { return http.get(`/tech/${id}`) }
/** 获取 SPORTS 记忆列表 */
export function fetchSportMemories()   { return http.get('/sports') }
/** 获取 MEDIA 条目列表（可选 category=MUSIC|MOVIE） */
export function fetchMediaEntries(category) {
  const params = category ? { category } : {}
  return http.get('/media', { params })
}

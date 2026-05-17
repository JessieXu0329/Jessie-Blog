import http from './http'

export const fetchPosts    = ()          => http.get('/posts')
export const fetchPostById = (id)        => http.get(`/posts/${id}`)
export const createPost    = (data, token) =>
  http.post('/posts', data, { headers: { 'X-Admin-Token': token } })
export const deletePost    = (id, token) =>
  http.delete(`/posts/${id}`, { headers: { 'X-Admin-Token': token } })

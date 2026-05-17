import { defineStore } from 'pinia'
import { ref } from 'vue'
import { fetchPosts, fetchPostById } from '../api/posts'

export const usePostsStore = defineStore('posts', () => {
  const list    = ref([])
  const current = ref(null)
  const loading = ref(false)
  const error   = ref(null)

  async function loadList() {
    loading.value = true
    error.value   = null
    try {
      list.value = await fetchPosts()
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function loadPost(id) {
    loading.value = true
    error.value   = null
    current.value = null
    try {
      current.value = await fetchPostById(id)
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  return { list, current, loading, error, loadList, loadPost }
})

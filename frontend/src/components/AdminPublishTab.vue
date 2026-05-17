<template>
  <div class="space-y-8">
    <!-- ══════════════════════════════════════════════════════════
         PUBLISH FORM
         ════════════════════════════════════════════════════════ -->
    <form @submit.prevent="publish" class="space-y-6" novalidate>
      <div class="space-y-2"><label class="field-label">Title</label><input v-model="form.title" type="text" class="sci-input" required /></div>

      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <div class="space-y-2"><label class="field-label">Category</label><select v-model="form.category" class="sci-input"><option v-for="c in CATEGORIES" :key="c" :value="c">{{ c }}</option></select></div>
        <div class="space-y-2">
          <label class="field-label">Mood {{ selectedGradientName }}</label>
          <div class="grid grid-cols-4 gap-2">
            <button v-for="g in GRADIENTS" :key="g.name" type="button" :title="g.name" @click="form.moodGradient = g.value"
                    class="h-10 rounded-lg border-2 transition-all duration-200"
                    :class="form.moodGradient === g.value ? 'border-white scale-105' : 'border-transparent opacity-60 hover:opacity-100 hover:scale-105'" :style="{ background: g.value }">
              <span v-if="form.moodGradient === g.value" class="text-white text-xs drop-shadow">✓</span>
            </button>
          </div>
        </div>
      </div>

      <div class="space-y-2"><label class="field-label">Summary</label><textarea v-model="form.summary" class="sci-input resize-none" rows="3" required /></div>

      <div class="space-y-2">
        <div class="flex items-center justify-between">
          <label class="field-label">Content (Markdown)</label>
          <div class="flex gap-1">
            <button v-for="tab in ['Write','Preview']" :key="tab" type="button" @click="contentTab=tab"
                    class="px-3 py-1 text-[10px] font-mono tracking-widest uppercase rounded border transition-colors duration-150"
                    :class="contentTab===tab?'border-blue-500/60 text-blue-300 bg-blue-500/10':'border-blue-900/40 text-slate-500 hover:text-slate-300'">{{tab}}</button>
          </div>
        </div>
        <textarea v-show="contentTab==='Write'" v-model="form.content" class="sci-input resize-y font-mono text-xs leading-relaxed" rows="18" required />
        <div v-show="contentTab==='Preview'" class="min-h-[320px] rounded-lg border border-blue-900/40 bg-slate-950/50 p-6 prose-scifi" v-html="previewHtml" />
      </div>

      <div class="flex items-center justify-between pt-2">
        <p class="text-[10px] font-mono text-slate-700 tracking-wider">// ESSAY ENTRY</p>
        <button type="submit" :disabled="submitting" class="admin-submit-btn">
          <span v-if="submitting" class="w-3.5 h-3.5 border border-sky-300 border-t-transparent rounded-full animate-spin" />
          {{ submitting ? 'TRANSMITTING...' : 'TRANSMIT →' }}
        </button>
      </div>
    </form>

    <!-- ══════════════════════════════════════════════════════════
         POSTS LIST
         ════════════════════════════════════════════════════════ -->
    <div>
      <div class="h-px bg-gradient-to-r from-sky-800/40 via-blue-700/10 to-transparent mb-5" />
      <p class="field-label mb-4">Published Posts · {{ posts.length }}</p>

      <p v-if="loading" class="font-mono text-[10px] tracking-wider text-slate-600 animate-pulse">// LOADING...</p>
      <div v-else-if="posts.length === 0" class="card-glass p-6 text-center">
        <p class="font-mono text-xs tracking-wider text-slate-600">// NO POSTS YET</p>
      </div>

      <div v-else class="space-y-2">
        <div v-for="p in posts" :key="p.id" class="card-glass p-4 flex items-center gap-4 group">
          <div class="flex-1 min-w-0">
            <p class="font-mono text-xs tracking-wide text-slate-200 truncate">{{ p.title }}</p>
            <p class="text-[10px] text-slate-500 mt-0.5 line-clamp-1">{{ p.summary }}</p>
          </div>
          <span class="font-mono text-[9px] tracking-wider px-2 py-0.5 rounded border shrink-0 border-sky-500/20 text-sky-400 bg-sky-500/10">{{ p.category }}</span>
          <button type="button" :disabled="deleting === p.id" @click="remove(p.id)"
                  class="shrink-0 px-3 py-1.5 rounded font-mono text-[10px] tracking-wider uppercase border border-red-500/20 text-red-400/60 hover:text-red-400 hover:border-red-500/50 hover:bg-red-500/10 disabled:opacity-40 disabled:cursor-not-allowed transition-all duration-200 opacity-0 group-hover:opacity-100">
            {{ deleting === p.id ? '...' : 'DEL' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { marked } from 'marked'
import { createPost, fetchPosts, deletePost } from '../api/posts'

const props = defineProps({ token: { type: String, required: true } })
const emit = defineEmits(['toast'])

const CATEGORIES = ['Tech', 'Essay', 'Media']
const GRADIENTS = [
  { name: 'Aurora',   value: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' },
  { name: 'Cyber',    value: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)' },
  { name: 'Neon',     value: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)' },
  { name: 'Sunset',   value: 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)' },
  { name: 'Matrix',   value: 'linear-gradient(135deg, #0a0a0a 0%, #00ff41 100%)' },
  { name: 'Ocean',    value: 'linear-gradient(135deg, #0f2027 0%, #2c5364 100%)' },
  { name: 'Hologram', value: 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)' },
  { name: 'Midnight', value: 'linear-gradient(135deg, #0d0d1a 0%, #1a1aff 100%)' },
]

const form = ref({ title: '', summary: '', content: '', category: 'Tech', moodGradient: GRADIENTS[0].value })
const selectedGradientName = computed(() => GRADIENTS.find(g => g.value === form.value.moodGradient)?.name ?? 'Custom')
const contentTab = ref('Write')
const previewHtml = computed(() => form.value.content ? marked.parse(form.value.content) : '<p class="text-slate-600">Nothing to preview...</p>')
const submitting = ref(false)

async function publish() {
  if (!props.token.trim()) return emit('toast', { ok: false, message: 'ACCESS TOKEN REQUIRED' })
  submitting.value = true
  try {
    await createPost(form.value, props.token.trim())
    emit('toast', { ok: true, message: 'ARTICLE PUBLISHED' })
    form.value = { title: '', summary: '', content: '', category: 'Tech', moodGradient: GRADIENTS[0].value }
    await loadPosts()
  } catch (err) {
    emit('toast', { ok: false, message: err.response?.status === 403 ? 'ACCESS DENIED' : 'PUBLISH FAILED' })
  } finally { submitting.value = false }
}

// ── Posts list ─────────────────────────────────────────────────
const posts = ref([])
const loading = ref(true)
const deleting = ref(null)

async function loadPosts() {
  loading.value = true
  try { const data = await fetchPosts(); posts.value = Array.isArray(data) ? data : [] } catch (err) { console.error(err) }
  finally { loading.value = false }
}

async function remove(id) {
  if (!confirm('Delete this post?')) return
  deleting.value = id
  try {
    await deletePost(id, props.token.trim())
    posts.value = posts.value.filter(p => p.id !== id)
    emit('toast', { ok: true, message: 'POST DELETED' })
  } catch (err) {
    const code = err.response?.status ?? 'NET'
    emit('toast', { ok: false, message: `DELETE FAILED (${code})` })
  } finally { deleting.value = null }
}

onMounted(() => loadPosts())
</script>

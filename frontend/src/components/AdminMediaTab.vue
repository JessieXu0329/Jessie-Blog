<template>
  <div class="space-y-8">
    <!-- ══════════════════════════════════════════════════════════
         UPLOAD FORM
         ════════════════════════════════════════════════════════ -->
    <form @submit.prevent="publish" class="space-y-6" novalidate>

      <!-- Category toggle -->
      <div class="space-y-2">
        <label class="field-label">Category</label>
        <div class="flex gap-1">
          <button
            v-for="c in ['MUSIC', 'MOVIE']"
            :key="c"
            type="button"
            @click="form.category = c"
            class="flex-1 py-2.5 font-mono text-xs tracking-widest uppercase rounded-lg border transition-all duration-200"
            :class="form.category === c
              ? 'border-sky-500/60 text-sky-300 bg-sky-500/10 shadow-[0_0_12px_rgba(56,189,248,0.15)]'
              : 'border-slate-700/50 text-slate-500 hover:text-slate-400 hover:border-slate-600'"
          >
            <span class="mr-1.5">{{ c === 'MUSIC' ? '⬡' : '◈' }}</span>
            {{ c }}
          </button>
        </div>
      </div>

      <!-- Image upload with preview -->
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <label
          class="flex flex-col items-center justify-center gap-2 h-48 rounded-lg border-2 border-dashed cursor-pointer transition-all duration-200 bg-slate-950/40 border-slate-700/50 hover:border-sky-500/40 hover:bg-slate-950/60"
          @dragover.prevent @dragleave.prevent @drop.prevent="onDrop"
        >
          <input ref="fileInput" type="file" accept="image/*" class="hidden" @change="onFileChange" />
          <template v-if="!file">
            <span class="text-2xl text-slate-600">⬆</span>
            <span class="font-mono text-[10px] tracking-wider text-slate-500">DROP FILE or CLICK</span>
            <span class="text-[10px] text-slate-700">JPG / PNG / WEBP</span>
          </template>
          <template v-else>
            <span class="text-sky-400 text-sm font-mono">{{ file.name }}</span>
            <span class="text-[10px] text-slate-500">{{ (file.size / 1024).toFixed(0) }} KB</span>
          </template>
        </label>
        <div class="h-48 rounded-lg border border-slate-800/60 overflow-hidden bg-slate-950/30 flex items-center justify-center">
          <img v-if="previewUrl" :src="previewUrl" alt="Preview" class="w-full h-full object-cover" />
          <span v-else class="font-mono text-[10px] tracking-wider text-slate-700">PREVIEW</span>
        </div>
      </div>

      <!-- Title + Subtitle -->
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <div class="space-y-2">
          <label class="field-label">Title</label>
          <input v-model="form.title" type="text" class="sci-input" required
                 :placeholder="form.category === 'MUSIC' ? 'Album name...' : 'Film title...'" />
        </div>
        <div class="space-y-2">
          <label class="field-label">{{ form.category === 'MUSIC' ? 'Artist' : 'Director' }}</label>
          <input v-model="form.subtitle" type="text" class="sci-input"
                 :placeholder="form.category === 'MUSIC' ? 'Artist name...' : 'Director name...'" />
        </div>
      </div>

      <!-- Review -->
      <div class="space-y-2">
        <label class="field-label">One‑Line Review</label>
        <textarea v-model="form.review" class="sci-input resize-none" rows="2" placeholder="A short impression..." />
      </div>

      <!-- Submit -->
      <div class="flex items-center justify-between pt-2">
        <p class="text-[10px] font-mono text-slate-700 tracking-wider">// MEDIA VAULT ENTRY</p>
        <button type="submit" :disabled="submitting || !file" class="admin-submit-btn">
          <span v-if="submitting" class="w-3.5 h-3.5 border border-sky-300 border-t-transparent rounded-full animate-spin" />
          {{ submitting ? 'SAVING...' : 'SAVE →' }}
        </button>
      </div>
    </form>

    <!-- ══════════════════════════════════════════════════════════
         ENTRY LIST
         ════════════════════════════════════════════════════════ -->
    <div>
      <div class="h-px bg-gradient-to-r from-sky-800/40 via-blue-700/10 to-transparent mb-5" />
      <p class="field-label mb-4">Existing Entries · {{ entries.length }}</p>

      <p v-if="loading" class="font-mono text-[10px] tracking-wider text-slate-600 animate-pulse">// LOADING...</p>

      <div v-else-if="entries.length === 0" class="card-glass p-6 text-center">
        <p class="font-mono text-xs tracking-wider text-slate-600">// NO ENTRIES YET</p>
      </div>

      <div v-else class="space-y-2">
        <!-- Flip card 3D wrapper -->
        <div
          v-for="e in entries"
          :key="e.id"
          class="flip-wrapper group"
          :class="{ 'is-flipped': flipped.has(e.id) }"
          @click="toggleFlip(e.id)"
        >
          <div class="flip-inner">
            <!-- ── FRONT ───────────────────────────────────── -->
            <div class="card-glass flip-front p-4 flex items-center gap-4 cursor-pointer">
              <div class="w-14 h-14 shrink-0 rounded overflow-hidden bg-slate-950 border border-slate-800/60">
                <img v-if="e.coverUrl" :src="e.coverUrl" class="w-full h-full object-cover" />
                <div v-else class="w-full h-full flex items-center justify-center">
                  <span class="font-mono text-lg font-bold text-slate-700">{{ e.title.charAt(0) }}</span>
                </div>
              </div>
              <div class="flex-1 min-w-0">
                <p class="font-mono text-xs tracking-wide text-slate-200 truncate">{{ e.title }}</p>
                <p class="font-mono text-[10px] tracking-wider text-slate-500">{{ e.subtitle }}</p>
              </div>
              <span class="font-mono text-[9px] tracking-wider px-2 py-0.5 rounded border shrink-0"
                    :class="e.category === 'MUSIC'
                      ? 'border-emerald-500/20 text-emerald-400 bg-emerald-500/10'
                      : 'border-violet-500/20 text-violet-400 bg-violet-500/10'">
                {{ e.category }}
              </span>
              <span class="font-mono text-[8px] tracking-wider text-slate-700 shrink-0">↻</span>

              <button type="button" :disabled="deleting === e.id" @click.stop="remove(e.id)"
                      class="shrink-0 px-3 py-1.5 rounded font-mono text-[10px] tracking-wider uppercase border border-red-500/20 text-red-400/60 hover:text-red-400 hover:border-red-500/50 hover:bg-red-500/10 disabled:opacity-40 disabled:cursor-not-allowed transition-all duration-200 opacity-0 group-hover:opacity-100">
                {{ deleting === e.id ? '...' : 'DEL' }}
              </button>
            </div>

            <!-- ── BACK: Review ────────────────────────────── -->
            <div class="card-glass flip-back p-5 flex items-center justify-center text-center cursor-pointer">
              <div>
                <p class="font-mono text-[10px] tracking-[0.3em] text-sky-400/60 uppercase mb-3">
                  // ONE-LINE REVIEW
                </p>
                <p class="text-slate-300 text-sm leading-relaxed italic">
                  {{ e.review || '(no review)' }}
                </p>
                <p class="font-mono text-[9px] tracking-wider text-slate-600 mt-4">
                  {{ e.title }} · {{ e.subtitle }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { adminUpload, adminDelete, fetchMediaEntries } from '../api/admin'

const props = defineProps({ token: { type: String, required: true } })
const emit = defineEmits(['toast'])

// ── Form ─────────────────────────────────────────────────────
const form = reactive({ title: '', subtitle: '', review: '', category: 'MUSIC' })
const fileInput = ref(null)
const file = ref(null)
const previewUrl = ref('')
const submitting = ref(false)

function setFile(f) {
  file.value = f
  if (previewUrl.value) URL.revokeObjectURL(previewUrl.value)
  previewUrl.value = f ? URL.createObjectURL(f) : ''
}
function onFileChange(e) { setFile(e.target.files[0] ?? null) }
function onDrop(e) { setFile(e.dataTransfer.files[0] ?? null) }

async function publish() {
  if (!props.token.trim()) return emit('toast', { ok: false, message: 'ACCESS TOKEN REQUIRED' })
  if (!file.value) return emit('toast', { ok: false, message: 'PLEASE SELECT AN IMAGE' })
  submitting.value = true
  try {
    await adminUpload('media', file.value, { ...form }, props.token.trim())
    emit('toast', { ok: true, message: `${form.category} ENTRY SAVED` })
    Object.assign(form, { title: '', subtitle: '', review: '', category: 'MUSIC' })
    file.value = null
    previewUrl.value = ''
    await loadEntries()
  } catch (err) {
    emit('toast', { ok: false, message: err.response?.status === 403 ? 'ACCESS DENIED' : 'UPLOAD FAILED' })
  } finally { submitting.value = false }
}

// ── Entry list ───────────────────────────────────────────────
const entries = ref([])
const loading = ref(true)
const deleting = ref(null)
const flipped = ref(new Set())

function toggleFlip(id) {
  const s = new Set(flipped.value)
  s.has(id) ? s.delete(id) : s.add(id)
  flipped.value = s
}

async function loadEntries() {
  loading.value = true
  try {
    const data = await fetchMediaEntries()
    entries.value = Array.isArray(data) ? data : []
  } catch (err) {
    console.error('Failed to load media entries:', err)
  } finally { loading.value = false }
}

async function remove(id) {
  if (!confirm('Delete this entry?')) return
  deleting.value = id
  try {
    await adminDelete('media', id, props.token.trim())
    entries.value = entries.value.filter(e => e.id !== id)
    emit('toast', { ok: true, message: 'ENTRY DELETED' })
  } catch (err) {
    const code = err.response?.status ?? 'NET'
    emit('toast', { ok: false, message: `DELETE FAILED (${code})` })
  } finally { deleting.value = null }
}

onMounted(() => loadEntries())
</script>

<style scoped>
/* ── 3D Flip ────────────────────────────────────────────────── */
.flip-wrapper {
  perspective: 800px;
}
.flip-inner {
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.5s cubic-bezier(0.23, 1, 0.32, 1);
}
.is-flipped .flip-inner {
  transform: rotateY(180deg);
}
.flip-front,
.flip-back {
  backface-visibility: hidden;
}
.flip-back {
  position: absolute;
  inset: 0;
  transform: rotateY(180deg);
  pointer-events: none;
}
.is-flipped .flip-back {
  pointer-events: auto;
}
.is-flipped .flip-front {
  pointer-events: none;
}
</style>

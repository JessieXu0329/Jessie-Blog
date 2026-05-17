<template>
  <div class="space-y-8">
    <!-- ══════════════════════════════════════════════════════════
         UPLOAD FORM
         ════════════════════════════════════════════════════════ -->
    <form @submit.prevent="publish" class="space-y-6" novalidate>
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <label class="flex flex-col items-center justify-center gap-2 h-48 rounded-lg border-2 border-dashed cursor-pointer transition-all duration-200 bg-slate-950/40 border-slate-700/50 hover:border-sky-500/40 hover:bg-slate-950/60" @dragover.prevent @dragleave.prevent @drop.prevent="onDrop">
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

      <div class="grid grid-cols-1 sm:grid-cols-3 gap-5">
        <div class="space-y-2"><label class="field-label">Title</label><input v-model="form.title" type="text" class="sci-input" required /></div>
        <div class="space-y-2"><label class="field-label">Role</label><input v-model="form.role" type="text" class="sci-input" /></div>
        <div class="space-y-2"><label class="field-label">Year</label><input v-model="form.year" type="text" class="sci-input" /></div>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <div class="space-y-2"><label class="field-label">Tech Stack</label><input v-model="form.techStack" type="text" class="sci-input" /></div>
        <div class="space-y-2"><label class="field-label">Status</label><select v-model="form.status" class="sci-input"><option value="IN_PROGRESS">IN PROGRESS</option><option value="DEPLOYED">DEPLOYED</option></select></div>
      </div>

      <div class="space-y-2"><label class="field-label">Description</label><textarea v-model="form.description" class="sci-input resize-none" rows="3" /></div>
      <div class="space-y-2"><label class="field-label">GitHub URL</label><input v-model="form.githubUrl" type="url" class="sci-input" /></div>

      <div class="flex items-center justify-between pt-2">
        <p class="text-[10px] font-mono text-slate-700 tracking-wider">// TECH ARCHIVE ENTRY</p>
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
      <p class="field-label mb-4">Existing Projects · {{ entries.length }}</p>

      <p v-if="loading" class="font-mono text-[10px] tracking-wider text-slate-600 animate-pulse">// LOADING...</p>
      <div v-else-if="entries.length === 0" class="card-glass p-6 text-center">
        <p class="font-mono text-xs tracking-wider text-slate-600">// NO PROJECTS YET</p>
      </div>

      <div v-else class="space-y-2">
        <div v-for="e in entries" :key="e.id" class="card-glass p-4 flex items-center gap-4 group">
          <div class="w-12 h-12 shrink-0 rounded overflow-hidden bg-slate-950 border border-slate-800/60">
            <img v-if="e.screenshotUrl" :src="e.screenshotUrl" class="w-full h-full object-cover" />
            <div v-else class="w-full h-full flex items-center justify-center"><span class="font-mono text-lg font-bold text-slate-700">{{ e.title.charAt(0) }}</span></div>
          </div>
          <div class="flex-1 min-w-0">
            <p class="font-mono text-xs tracking-wide text-slate-200 truncate">{{ e.title }}</p>
            <p class="font-mono text-[10px] tracking-wider text-slate-500">{{ e.role }} · {{ e.year }}</p>
          </div>
          <span class="font-mono text-[9px] tracking-wider px-2 py-0.5 rounded border shrink-0"
                :class="e.status === 'DEPLOYED' ? 'border-emerald-500/20 text-emerald-400 bg-emerald-500/10' : 'border-sky-500/20 text-sky-400 bg-sky-500/10'">
            {{ e.status }}
          </span>
          <button type="button" :disabled="deleting === e.id" @click="remove(e.id)"
                  class="shrink-0 px-3 py-1.5 rounded font-mono text-[10px] tracking-wider uppercase border border-red-500/20 text-red-400/60 hover:text-red-400 hover:border-red-500/50 hover:bg-red-500/10 disabled:opacity-40 disabled:cursor-not-allowed transition-all duration-200 opacity-0 group-hover:opacity-100">
            {{ deleting === e.id ? '...' : 'DEL' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { adminUpload, adminDelete, fetchTechProjects } from '../api/admin'

const props = defineProps({ token: { type: String, required: true } })
const emit = defineEmits(['toast'])

const form = reactive({ title: '', description: '', techStack: '', githubUrl: '', role: '', status: 'IN_PROGRESS', year: '' })
const fileInput = ref(null); const file = ref(null); const previewUrl = ref(''); const submitting = ref(false)

function setFile(f) { file.value = f; if (previewUrl.value) URL.revokeObjectURL(previewUrl.value); previewUrl.value = f ? URL.createObjectURL(f) : '' }
function onFileChange(e) { setFile(e.target.files[0] ?? null) }
function onDrop(e) { setFile(e.dataTransfer.files[0] ?? null) }

async function publish() {
  if (!props.token.trim()) return emit('toast', { ok: false, message: 'ACCESS TOKEN REQUIRED' })
  if (!file.value) return emit('toast', { ok: false, message: 'PLEASE SELECT AN IMAGE' })
  submitting.value = true
  try {
    await adminUpload('tech', file.value, { ...form }, props.token.trim())
    emit('toast', { ok: true, message: 'TECH PROJECT SAVED' })
    Object.assign(form, { title: '', description: '', techStack: '', githubUrl: '', role: '', status: 'IN_PROGRESS', year: '' })
    file.value = null; previewUrl.value = ''
    await loadEntries()
  } catch (err) {
    emit('toast', { ok: false, message: err.response?.status === 403 ? 'ACCESS DENIED' : 'UPLOAD FAILED' })
  } finally { submitting.value = false }
}

const entries = ref([]); const loading = ref(true); const deleting = ref(null)

async function loadEntries() {
  loading.value = true
  try { const data = await fetchTechProjects(); entries.value = Array.isArray(data) ? data : [] } catch (err) { console.error(err) }
  finally { loading.value = false }
}

async function remove(id) {
  if (!confirm('Delete this project?')) return
  deleting.value = id
  try { await adminDelete('tech', id, props.token.trim()); entries.value = entries.value.filter(e => e.id !== id); emit('toast', { ok: true, message: 'PROJECT DELETED' }) }
  catch (err) { emit('toast', { ok: false, message: `DELETE FAILED (${err.response?.status ?? 'NET'})` }) }
  finally { deleting.value = null }
}

onMounted(() => loadEntries())
</script>

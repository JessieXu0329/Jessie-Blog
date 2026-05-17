<template>
  <div class="relative z-10 min-h-screen pt-14 flex items-center justify-center px-6">

    <div class="w-full max-w-sm animate-fade-slide-up">

      <!-- Terminal header -->
      <div class="mb-8 text-center">
        <p class="font-mono text-[10px] tracking-[0.4em] text-blue-500 uppercase mb-4">
          [ SECURE ACCESS REQUIRED ]
        </p>
        <!-- ASCII lock -->
        <pre class="font-mono text-blue-900/80 text-xs leading-tight select-none">
  ╔══════╗
  ║ ░░░░ ║
  ╚══╗╔══╝
  ┌──╨╨──┐
  │  ██  │
  │  ██  │
  └──────┘</pre>
        <h1 class="mt-4 text-2xl font-bold tracking-widest text-slate-200 uppercase">
          Admin Terminal
        </h1>
      </div>

      <!-- Login card -->
      <div
        class="card-glass p-8 space-y-5"
        :class="shake ? 'animate-[shake_0.4s_ease]' : ''"
      >
        <div class="space-y-2">
          <label class="block text-[10px] font-mono tracking-[0.25em] text-slate-500 uppercase">
            Password
          </label>
          <div class="relative">
            <input
              ref="inputRef"
              v-model="password"
              :type="showPwd ? 'text' : 'password'"
              class="w-full rounded-lg px-4 py-3 pr-14
                     bg-slate-950/70 border text-slate-200 text-sm font-mono
                     placeholder-slate-600
                     focus:outline-none focus:ring-1 transition-colors duration-200"
              :class="error
                ? 'border-red-500/60 focus:border-red-400 focus:ring-red-500/25'
                : 'border-blue-900/50 focus:border-blue-500/60 focus:ring-blue-500/25'"
              placeholder="············"
              autocomplete="current-password"
              @keydown.enter="submit"
            />
            <button
              type="button"
              @click="showPwd = !showPwd"
              class="absolute right-3 top-1/2 -translate-y-1/2
                     text-slate-600 hover:text-slate-300 transition-colors text-[10px] font-mono"
            >
              {{ showPwd ? 'HIDE' : 'SHOW' }}
            </button>
          </div>
          <p v-if="error" class="text-xs font-mono text-red-400">
            ✗ &nbsp;ACCESS DENIED
          </p>
        </div>

        <button
          @click="submit"
          :disabled="loading || !password"
          class="w-full flex items-center justify-center gap-2.5 py-3 rounded-lg
                 font-mono text-sm tracking-widest uppercase
                 border border-blue-500/60 text-blue-200
                 bg-blue-600/20 hover:bg-blue-600/30
                 disabled:opacity-40 disabled:cursor-not-allowed
                 transition-all duration-200
                 shadow-[0_0_14px_rgba(59,130,246,0.2)]
                 hover:shadow-[0_0_22px_rgba(59,130,246,0.4)]"
        >
          <span
            v-if="loading"
            class="w-3.5 h-3.5 border border-blue-300 border-t-transparent rounded-full animate-spin"
          />
          {{ loading ? 'VERIFYING...' : 'AUTHENTICATE →' }}
        </button>
      </div>

      <!-- Back link -->
      <p class="mt-6 text-center">
        <RouterLink
          to="/"
          class="text-xs font-mono text-slate-700 hover:text-slate-400 transition-colors"
        >
          ← RETURN TO INDEX
        </RouterLink>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { useAdminAuth } from '../composables/useAdminAuth'

const router  = useRouter()
const { login } = useAdminAuth()

const password = ref('')
const showPwd  = ref(false)
const loading  = ref(false)
const error    = ref(false)
const shake    = ref(false)
const inputRef = ref(null)

async function submit() {
  if (!password.value || loading.value) return
  loading.value = true
  error.value   = false

  const ok = await login(password.value)

  if (ok) {
    router.replace('/admin')
  } else {
    error.value = true
    shake.value = true
    password.value = ''
    setTimeout(() => { shake.value = false }, 450)
    await nextTick()
    inputRef.value?.focus()
  }

  loading.value = false
}
</script>

<style scoped>
@keyframes shake {
  0%, 100% { transform: translateX(0); }
  20%       { transform: translateX(-8px); }
  40%       { transform: translateX(8px); }
  60%       { transform: translateX(-5px); }
  80%       { transform: translateX(5px); }
}
</style>

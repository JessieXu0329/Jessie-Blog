<template>
  <div class="relative z-10 min-h-screen pt-14 flex">

    <!-- ═══════════════════════════════════════════════════════════════
         SIDEBAR
         ════════════════════════════════════════════════════════════ -->
    <aside class="hidden md:flex flex-col w-52 shrink-0
                  border-r border-slate-800/60
                  bg-[#020617]/50 backdrop-blur-sm
                  h-[calc(100vh-3.5rem)] sticky top-14">
      <!-- Sidebar header -->
      <div class="px-5 pt-7 pb-5">
        <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase">
          [ ADMIN ]
        </p>
        <p class="font-mono text-[8px] tracking-[0.3em] text-slate-600 uppercase mt-2">
          Control Terminal
        </p>
      </div>

      <!-- Nav items -->
      <nav class="flex-1 px-3 space-y-px">
        <button
          v-for="item in NAV"
          :key="item.id"
          @click="activeNav = item.id"
          class="w-full flex items-center gap-3 px-3 py-3 rounded-lg
                 font-mono text-xs tracking-[0.15em] uppercase
                 transition-all duration-200 text-left"
          :class="activeNav === item.id
            ? 'bg-sky-500/10 border border-sky-500/20 text-sky-300 shadow-[inset_0_0_12px_rgba(56,189,248,0.06)]'
            : 'border border-transparent text-slate-500 hover:text-slate-300 hover:bg-slate-900/40'"
        >
          <span class="text-sm shrink-0">{{ item.icon }}</span>
          <span>{{ item.label }}</span>
        </button>
      </nav>

      <!-- Sidebar footer -->
      <div class="px-5 py-5 border-t border-slate-800/40 space-y-3">
        <div class="flex items-center gap-1.5">
          <span class="w-1.5 h-1.5 rounded-full bg-sky-500 animate-pulse" />
          <span class="font-mono text-[9px] tracking-wider text-slate-600">ONLINE</span>
        </div>
        <button
          @click="handleLogout"
          class="w-full text-left font-mono text-[10px] tracking-widest uppercase
                 text-slate-600 hover:text-red-400 transition-colors"
        >
          LOGOUT ⏻
        </button>
      </div>
    </aside>

    <!-- ═══════════════════════════════════════════════════════════════
         MAIN CONTENT
         ════════════════════════════════════════════════════════════ -->
    <div class="flex-1 min-w-0">

      <!-- Mobile nav (horizontal scroll) -->
      <div class="md:hidden px-5 pt-5 pb-2 overflow-x-auto">
        <div class="flex gap-1">
          <button
            v-for="item in NAV"
            :key="item.id"
            @click="activeNav = item.id"
            class="shrink-0 flex items-center gap-2 px-4 py-2 rounded-lg
                   font-mono text-xs tracking-[0.15em] uppercase
                   transition-all duration-200"
            :class="activeNav === item.id
              ? 'bg-sky-500/10 border border-sky-500/20 text-sky-300'
              : 'border border-transparent text-slate-500 hover:text-slate-300'"
          >
            <span>{{ item.icon }}</span>
            <span>{{ item.label }}</span>
          </button>
        </div>
      </div>

      <div class="max-w-3xl mx-auto px-5 md:px-10 py-8 md:py-12">

        <!-- ── Toast ──────────────────────────────────────────── -->
        <Transition name="toast">
          <div
            v-if="toast.visible"
            class="fixed top-20 left-1/2 -translate-x-1/2 z-50
                   flex items-center gap-3 px-6 py-3 rounded-lg
                   font-mono text-sm border backdrop-blur-md
                   shadow-[0_0_24px_rgba(0,0,0,0.5)]"
            :class="toast.ok
              ? 'bg-cyan-950/95 border-cyan-500/70 text-cyan-200'
              : 'bg-red-950/95  border-red-500/70  text-red-200'"
          >
            <span class="text-base">{{ toast.ok ? '✓' : '✗' }}</span>
            {{ toast.message }}
          </div>
        </Transition>

        <!-- ── Header ─────────────────────────────────────────── -->
        <div class="mb-8 animate-fade-slide-up">
          <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-3">
            [ ADMIN TERMINAL ]
          </p>
          <h1 class="text-3xl font-bold
                     bg-gradient-to-br from-white via-blue-100 to-blue-300
                     bg-clip-text text-transparent">
            {{ activeNavLabel }}
          </h1>
        </div>

        <!-- ── Token field ────────────────────────────────────── -->
        <fieldset class="card-glass p-5 mb-8 space-y-2">
          <legend class="field-label mb-1">Access Token</legend>
          <div class="relative">
            <input
              v-model="token"
              :type="showToken ? 'text' : 'password'"
              class="sci-input pr-16"
              placeholder="Your admin token..."
              autocomplete="off"
            />
            <button
              type="button"
              @click="showToken = !showToken"
              class="absolute right-3 top-1/2 -translate-y-1/2
                     text-slate-500 hover:text-slate-300 transition-colors text-xs font-mono"
            >
              {{ showToken ? 'HIDE' : 'SHOW' }}
            </button>
          </div>
          <p class="text-[10px] font-mono text-slate-600 tracking-wider">
            // {{ activeNavLabel }} — REQUIRED FOR UPLOAD
          </p>
        </fieldset>

        <!-- ── Tab Content ────────────────────────────────────── -->
        <Transition name="tab" mode="out-in">
          <component
            :is="activeComponent"
            :key="activeNav"
            :token="token"
            @toast="showToast"
          />
        </Transition>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useAdminAuth } from '../composables/useAdminAuth'
import AdminPublishTab from '../components/AdminPublishTab.vue'
import AdminTechTab    from '../components/AdminTechTab.vue'
import AdminSportsTab  from '../components/AdminSportsTab.vue'
import AdminMediaTab   from '../components/AdminMediaTab.vue'

// ── Auth ──────────────────────────────────────────────────────────
const router = useRouter()
const { logout } = useAdminAuth()
function handleLogout() {
  logout()
  router.replace('/admin/login')
}

// ── Token ─────────────────────────────────────────────────────────
const TOKEN_KEY = 'jessie_admin_token'
const token     = ref(localStorage.getItem(TOKEN_KEY) ?? '')
const showToken = ref(false)
watch(token, (v) => localStorage.setItem(TOKEN_KEY, v))

// ── Sidebar navigation ────────────────────────────────────────────
const NAV = [
  { id: 'tech',   label: 'Tech',   icon: '⬡' },
  { id: 'essays', label: 'Essays', icon: '≡' },
  { id: 'sports', label: 'Sports', icon: '▵' },
  { id: 'media',  label: 'Media',  icon: '◈' },
]

const NAV_COMPONENTS = {
  tech:   AdminTechTab,
  essays: AdminPublishTab,
  sports: AdminSportsTab,
  media:  AdminMediaTab,
}

const activeNav  = ref(NAV[0].id)
const activeComponent = computed(() => NAV_COMPONENTS[activeNav.value])
const activeNavLabel  = computed(() => {
  const item = NAV.find(n => n.id === activeNav.value)
  return item ? item.label : 'Dashboard'
})

// ── Toast (shared) ────────────────────────────────────────────────
const toast = ref({ visible: false, ok: true, message: '' })
let toastTimer = null

function showToast({ ok, message }) {
  clearTimeout(toastTimer)
  toast.value = { visible: true, ok, message }
  toastTimer  = setTimeout(() => { toast.value.visible = false }, 3500)
}
</script>

<style scoped>
/* Tab transition */
.tab-enter-active,
.tab-leave-active  { transition: opacity 0.18s ease, transform 0.18s ease; }
.tab-enter-from    { opacity: 0; transform: translateY(6px); }
.tab-leave-to      { opacity: 0; transform: translateY(-4px); }

/* Toast */
.toast-enter-active,
.toast-leave-active { transition: opacity 0.3s ease, transform 0.3s ease; }
.toast-enter-from   { opacity: 0; transform: translate(-50%, -16px); }
.toast-leave-to     { opacity: 0; transform: translate(-50%, -10px); }
</style>

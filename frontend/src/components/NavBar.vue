<template>
  <header
    class="fixed top-0 inset-x-0 z-50 h-14
           bg-[#020617]/80 backdrop-blur-md
           border-b border-slate-800/70"
  >
    <div class="max-w-7xl mx-auto px-5 sm:px-8 h-full flex items-center">

      <!-- ── Left: Brand (non-clickable) ───────────────────── -->
      <div class="flex-1 flex items-center">
        <span class="flex items-center gap-px select-none cursor-default">
          <span class="brand-text">&gt;&nbsp;JESSIE</span>
          <span class="brand-text animate-blink-cursor">_</span>
        </span>
      </div>

      <!-- ── Center: Desktop nav links ─────────────────────── -->
      <nav class="hidden md:flex items-center gap-8">
        <RouterLink
          v-for="link in NAV_LINKS"
          :key="link.to"
          :to="link.to"
          class="nav-link"
          active-class="nav-link--active"
        >
          {{ link.label }}
        </RouterLink>
      </nav>

      <!-- ── Right: Status + Admin ─────────────────────────── -->
      <div class="flex-1 hidden md:flex items-center justify-end gap-5">
        <!-- Online indicator -->
        <div class="flex items-center gap-1.5">
          <span class="w-1.5 h-1.5 rounded-full bg-sky-500 animate-pulse" />
          <span class="text-[10px] font-mono tracking-widest text-slate-600">ONLINE</span>
        </div>

        <!-- Admin entry -->
        <RouterLink
          to="/admin"
          class="admin-link"
          active-class="admin-link--active"
        >
          ADMIN
        </RouterLink>
      </div>

      <!-- ── Mobile: hamburger ──────────────────────────────── -->
      <button
        class="md:hidden ml-auto p-2 text-slate-400 hover:text-sky-400
               transition-colors duration-200 rounded"
        :aria-label="menuOpen ? 'Close menu' : 'Open menu'"
        @click="menuOpen = !menuOpen"
      >
        <div class="flex flex-col justify-center gap-[5px] w-5 h-5">
          <span class="block h-px bg-current rounded-full origin-center transition-all duration-300"
                :class="menuOpen ? 'rotate-45 translate-y-[6px]' : 'w-5'" />
          <span class="block h-px bg-current rounded-full transition-all duration-200"
                :class="menuOpen ? 'opacity-0 w-0' : 'w-4'" />
          <span class="block h-px bg-current rounded-full origin-center transition-all duration-300"
                :class="menuOpen ? '-rotate-45 -translate-y-[6px]' : 'w-5'" />
        </div>
      </button>

    </div>

    <!-- ── Mobile dropdown ────────────────────────────────────── -->
    <Transition name="mobile-menu">
      <div
        v-if="menuOpen"
        class="md:hidden border-t border-slate-800/70
               bg-[#020617]/95 backdrop-blur-md"
      >
        <nav class="max-w-7xl mx-auto px-5 py-3 flex flex-col gap-1">
          <RouterLink
            v-for="link in NAV_LINKS"
            :key="link.to"
            :to="link.to"
            class="mobile-link"
            active-class="mobile-link--active"
            @click="menuOpen = false"
          >
            <span class="text-slate-700 mr-2">//</span>{{ link.label }}
          </RouterLink>
        </nav>

        <div class="flex items-center justify-between px-5 pb-4">
          <div class="flex items-center gap-1.5">
            <span class="w-1 h-1 rounded-full bg-sky-500 animate-pulse" />
            <span class="text-[10px] font-mono tracking-widest text-slate-700">ONLINE</span>
          </div>
          <RouterLink
            to="/admin"
            class="admin-link text-[10px]"
            @click="menuOpen = false"
          >
            ADMIN
          </RouterLink>
        </div>
      </div>
    </Transition>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const menuOpen = ref(false)
const router   = useRouter()
router.afterEach(() => { menuOpen.value = false })

const NAV_LINKS = [
  { label: 'ABOUT',  to: '/about'  },
  { label: 'TECH',   to: '/tech'   },
  { label: 'ESSAYS', to: '/essays' },
  { label: 'SPORTS', to: '/sports' },
  { label: 'MEDIA',  to: '/media'  },
]
</script>

<style scoped>
/* ── Brand ──────────────────────────────────────────────────── */
.brand-text {
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-weight: 700;
  font-size: 0.9rem;
  letter-spacing: 0.12em;
  color: #38bdf8;
  text-shadow: 0 0 16px rgba(56, 189, 248, 0.45);
}

/* ── Desktop nav link ───────────────────────────────────────── */
.nav-link {
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.72rem;
  font-weight: 500;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  position: relative;
  padding: 2px 0;
  /* 提亮基础色：slate-300 而非 slate-500 */
  color: rgb(203 213 225);
  transition: color 0.2s ease;
}

/* 底部动态高亮线 */
.nav-link::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, #38bdf8, #818cf8);
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.25s ease;
}

/* hover：渐变文字 + 底线展开 */
.nav-link:hover {
  background: linear-gradient(90deg, #38bdf8 0%, #818cf8 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}
.nav-link:hover::after { transform: scaleX(1); }

/* active：常亮渐变文字 + 底线 */
.nav-link--active {
  background: linear-gradient(90deg, #38bdf8 0%, #818cf8 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  filter: drop-shadow(0 0 6px rgba(56, 189, 248, 0.55));
}
.nav-link--active::after { transform: scaleX(1); }

/* ── Admin link ─────────────────────────────────────────────── */
.admin-link {
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.62rem;
  font-weight: 500;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  padding: 3px 8px;
  border-radius: 4px;
  border: 1px solid rgba(56, 189, 248, 0.2);
  color: rgb(100 116 139); /* slate-500 */
  transition: color 0.2s, border-color 0.2s, box-shadow 0.2s;
}
.admin-link:hover {
  color: #38bdf8;
  border-color: rgba(56, 189, 248, 0.5);
  box-shadow: 0 0 10px rgba(56, 189, 248, 0.2);
}
.admin-link--active {
  color: #38bdf8;
  border-color: rgba(56, 189, 248, 0.5);
}

/* ── Mobile nav link ────────────────────────────────────────── */
.mobile-link {
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.75rem;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  padding: 10px 12px;
  border-radius: 6px;
  color: rgb(203 213 225);
  transition: color 0.2s, background 0.2s;
}
.mobile-link:hover {
  color: #38bdf8;
  background: rgba(56, 189, 248, 0.06);
}
.mobile-link--active {
  color: #38bdf8;
  background: rgba(56, 189, 248, 0.08);
}

/* ── Mobile menu transition ─────────────────────────────────── */
.mobile-menu-enter-active,
.mobile-menu-leave-active { transition: opacity 0.2s ease, transform 0.2s ease; }
.mobile-menu-enter-from,
.mobile-menu-leave-to     { opacity: 0; transform: translateY(-8px); }
</style>

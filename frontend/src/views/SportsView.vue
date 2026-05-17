<template>
  <div class="relative z-10 min-h-screen pt-14">
    <div class="max-w-5xl mx-auto px-6 py-16">

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 1 — HERO
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-20 animate-fade-slide-up">
        <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-5">
          [ PHYSICAL ARCHIVE ]
        </p>
        <h1 class="text-3xl sm:text-4xl font-bold tracking-widest
                   bg-gradient-to-br from-white to-sky-300
                   bg-clip-text text-transparent mb-5">
          Sports
        </h1>
        <div class="max-w-xl space-y-2">
          <p class="text-slate-300 text-sm sm:text-base leading-relaxed">
            Movement, memory, and the feeling of being alive.
          </p>
          <p class="text-slate-500 text-sm leading-relaxed">
            身体、记忆，以及活着的实感。
          </p>
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 2 — PHOTO GALLERY
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-24">
        <!-- Section header -->
        <div class="mb-10 animate-fade-slide-up" style="animation-delay:0ms">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
            // MOMENTS
          </p>
          <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase">
            运动记忆 · MOVEMENT ARCHIVE
          </p>
        </div>

        <!-- Empty / loading -->
        <p v-if="loading" class="font-mono text-[10px] tracking-wider text-slate-700 uppercase animate-pulse">
          // SYNCING ARCHIVE...
        </p>
        <p v-else-if="memories.length === 0" class="font-mono text-xs tracking-wider text-slate-600">
          // NO MEMORIES YET — USE ADMIN PANEL TO ADD
        </p>

        <!-- Irregular photo grid -->
        <div
          v-else
          class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3
                 gap-4 auto-rows-[260px] grid-flow-row-dense"
        >
          <MemoryCard
            v-for="(m, idx) in memories"
            :key="m.id"
            :title="m.title"
            :location="m.location"
            :date="m.date"
            :description="m.description"
            :src="m.photoUrl"
            :gradient="gradientFor(m.title)"
            :size="sizeFor(idx)"
            class="animate-fade-slide-up"
            :style="{ animationDelay: `${idx * 80}ms` }"
          />
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 2.5 — DIVIDER
           ════════════════════════════════════════════════════════════ -->
      <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent mb-20" />

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 3 — SPORTS MEMORY (editorial prose)
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-20 animate-fade-slide-up" style="animation-delay:80ms">
        <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
          // SPORTS MEMORY
        </p>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-10">
          身体书写 · BODY MEMOIR
        </p>

        <!-- Editorial prose block -->
        <div class="max-w-xl">
          <p class="text-slate-300 text-sm sm:text-base leading-7 sm:leading-8 mb-6">
            Some moments stay in the body longer than memory does.
          </p>

          <p class="text-slate-400 text-sm leading-7 mb-8 space-y-3">
            <span class="block">
              The sound of volleyball shoes on the floor,
            </span>
            <span class="block">
              summer air after running,
            </span>
            <span class="block">
              lights from empty gyms,
            </span>
            <span class="block">
              city wind during midnight walks.
            </span>
          </p>

          <p class="text-sky-300/70 text-sm sm:text-base leading-7 italic">
            Movement is sometimes the closest thing to freedom.
          </p>
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 4 — TERMINAL STATUS
           ════════════════════════════════════════════════════════════ -->
      <section class="animate-fade-slide-up" style="animation-delay:160ms">
        <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
          // CURRENTLY INTO
        </p>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-8">
          最近上头 · CURRENTLY HOOKED
        </p>

        <div
          class="relative rounded-xl overflow-hidden max-w-md
                 bg-[#020a14] border border-slate-800/60
                 shadow-[inset_0_0_40px_rgba(0,0,0,0.4)]"
        >
          <!-- Terminal bar -->
          <div class="flex items-center gap-1.5 px-4 py-3 border-b border-slate-800/40 bg-slate-900/30">
            <span class="w-2.5 h-2.5 rounded-full bg-red-500/60" />
            <span class="w-2.5 h-2.5 rounded-full bg-yellow-500/60" />
            <span class="w-2.5 h-2.5 rounded-full bg-emerald-500/60" />
            <span class="ml-2 font-mono text-[10px] tracking-wider text-slate-600">
              body@jessie:~/
            </span>
          </div>

          <!-- Terminal body -->
          <div class="px-5 py-5 font-mono text-sm leading-8 text-slate-300">
            <p class="text-sky-400/80 mb-4">
              <span class="text-slate-500">&gt;</span> recently_into
            </p>

            <div class="space-y-1 mb-5">
              <p v-for="s in statusLines" :key="s" class="flex items-center gap-2">
                <span class="text-sky-500/60 text-xs">&bull;</span>
                <span class="text-slate-300">{{ s }}</span>
              </p>
            </div>

            <!-- Blinking cursor -->
            <p class="flex items-center gap-1.5 text-sky-400">
              <span class="text-slate-500">&gt;</span>
              <span class="inline-block w-2 h-4 bg-sky-400 animate-blink-cursor align-middle" />
            </p>
          </div>
        </div>
      </section>

      <!-- Bottom breathing space -->
      <div class="h-16" />

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import MemoryCard from '../components/MemoryCard.vue'
import { fetchSportMemories } from '../api/admin'

// ── Fetch from backend ───────────────────────────────────────────────
const memories = ref([])
const loading  = ref(true)

onMounted(async () => {
  try {
    const data = await fetchSportMemories()
    memories.value = Array.isArray(data) ? data : []
  } catch (err) {
    console.error('[SportsView] Failed to fetch:', err)
  } finally {
    loading.value = false
  }
})

// ── Gradient / size helpers (same palette as before for fallback) ───
const GRADIENTS = [
  'bg-gradient-to-br from-amber-900/50 via-orange-950/70 to-amber-950/90',
  'bg-gradient-to-br from-indigo-900/50 via-blue-950/70 to-purple-950/90',
  'bg-gradient-to-br from-slate-800/60 via-zinc-900/70 to-slate-950/90',
  'bg-gradient-to-br from-yellow-900/40 via-amber-950/60 to-orange-950/90',
  'bg-gradient-to-br from-cyan-900/40 via-blue-950/60 to-indigo-950/90',
  'bg-gradient-to-br from-rose-900/30 via-pink-950/50 to-rose-950/90',
]
const SIZES = ['lg', 'tall', 'sm', 'sm', 'lg', 'sm']

function gradientFor(title) { return GRADIENTS[title.length % GRADIENTS.length] }
function sizeFor(idx) { return SIZES[idx % SIZES.length] }

// ── Terminal status lines ────────────────────────────────────────────
const statusLines = ['攀岩', '游泳', '跑步', '健身', '羽毛球']
</script>

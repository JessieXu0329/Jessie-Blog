<template>
  <div class="relative z-10 min-h-screen pt-14">

    <!-- ── Hero ─────────────────────────────────────────────── -->
    <section class="relative flex flex-col items-center justify-center
                    px-6 pt-24 pb-16 text-center overflow-hidden">

      <!-- Glowing ring behind title -->
      <div class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2
                  w-[480px] h-[200px] rounded-full blur-3xl
                  bg-blue-600/12 pointer-events-none" />

      <!-- Label -->
      <p class="font-mono text-xs tracking-[0.3em] text-blue-400 uppercase mb-4 animate-pulse">
        [ SIGNAL RECEIVED ]
      </p>

      <!-- Main title -->
      <!-- pb-2: bg-clip-text 在行高边界会裁切渐变，需要留出底部空间 -->
      <h1 class="pb-2 text-5xl md:text-6xl font-bold leading-tight text-glow
                 bg-gradient-to-b from-white via-blue-100 to-blue-300
                 bg-clip-text text-transparent">
        Jessie's Blog
      </h1>

      <!-- Sub -->
      <p class="mt-4 text-sm font-mono text-slate-500 tracking-widest">
        TECH · ESSAY · MEDIA &nbsp;// &nbsp;EXPLORING THE DIGITAL FRONTIER
      </p>

      <!-- Category filter -->
      <div class="mt-10 flex flex-wrap justify-center gap-2">
        <button
          v-for="tab in TABS"
          :key="tab.value"
          @click="activeCategory = tab.value"
          class="px-4 py-1.5 rounded-full text-xs font-mono tracking-widest uppercase
                 border transition-all duration-200"
          :class="activeCategory === tab.value
            ? 'bg-blue-600/30 border-blue-400/70 text-blue-200 shadow-[0_0_12px_rgba(59,130,246,0.35)]'
            : 'border-blue-900/50 text-slate-500 hover:border-blue-700/60 hover:text-slate-300'"
        >
          {{ tab.label }}
        </button>
      </div>
    </section>

    <!-- ── Post grid ──────────────────────────────────────────── -->
    <section class="max-w-6xl mx-auto px-6 pb-24">

      <!-- Loading skeletons -->
      <div v-if="store.loading"
           class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        <div
          v-for="n in 6" :key="n"
          class="rounded-xl border border-blue-900/30 bg-slate-900/40 h-56 animate-pulse"
        />
      </div>

      <!-- Error state -->
      <div v-else-if="store.error"
           class="flex flex-col items-center gap-3 py-24 font-mono text-center">
        <span class="text-3xl text-red-400">⚠</span>
        <p class="text-slate-400 text-sm">无法连接到数据源</p>
        <p class="text-slate-600 text-xs">{{ store.error }}</p>
        <button
          @click="store.loadList()"
          class="mt-2 px-4 py-1.5 text-xs border border-blue-700/50 rounded
                 text-blue-400 hover:bg-blue-600/20 transition"
        >
          RETRY
        </button>
      </div>

      <!-- Empty state -->
      <div v-else-if="filtered.length === 0"
           class="flex flex-col items-center gap-2 py-24 font-mono text-center">
        <p class="text-slate-500 text-sm">// NO DATA IN THIS SECTOR</p>
      </div>

      <!-- Cards -->
      <div v-else
           class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        <PostCard
          v-for="(post, i) in filtered"
          :key="post.id"
          :post="post"
          :index="i"
        />
      </div>

      <!-- Post count -->
      <p v-if="!store.loading && filtered.length > 0"
         class="mt-10 text-center font-mono text-xs text-slate-700 tracking-widest">
        — {{ filtered.length }} RECORDS LOADED —
      </p>
    </section>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { usePostsStore } from '../stores/posts'
import PostCard from '../components/PostCard.vue'

const store = usePostsStore()

const TABS = [
  { label: 'All',   value: '' },
  { label: 'Tech',  value: 'Tech' },
  { label: 'Essay', value: 'Essay' },
  { label: 'Media', value: 'Media' },
]

const activeCategory = ref('')

const filtered = computed(() =>
  activeCategory.value
    ? store.list.filter((p) => p.category === activeCategory.value)
    : store.list
)

onMounted(() => store.loadList())
</script>

<template>
  <div class="relative z-10 min-h-screen pt-14">

    <!-- Loading -->
    <div v-if="store.loading" class="flex flex-col items-center gap-6 pt-40 font-mono">
      <div class="w-8 h-8 border-2 border-blue-500 border-t-transparent
                  rounded-full animate-spin" />
      <p class="text-xs text-slate-500 tracking-widest animate-pulse">LOADING DATA...</p>
    </div>

    <!-- Error -->
    <div v-else-if="store.error"
         class="flex flex-col items-center gap-3 pt-40 text-center font-mono">
      <p class="text-slate-400 text-sm">{{ store.error }}</p>
      <RouterLink to="/" class="text-xs text-blue-400 hover:text-cyan-300 transition">
        ← RETURN TO INDEX
      </RouterLink>
    </div>

    <!-- Content -->
    <template v-else-if="post">

      <!-- Hero banner -->
      <div
        class="relative h-2 w-full"
        :style="{ background: post.moodGradient || 'linear-gradient(90deg,#1e3a8a,#0ea5e9)' }"
      />

      <article class="max-w-3xl mx-auto px-6 py-14 animate-fade-slide-up">

        <!-- Back -->
        <RouterLink
          to="/"
          class="inline-flex items-center gap-2 text-xs font-mono text-slate-500
                 hover:text-cyan-300 transition-colors mb-10"
        >
          <span>←</span> BACK TO INDEX
        </RouterLink>

        <!-- Meta -->
        <div class="flex items-center gap-3 mb-5">
          <span
            class="px-2 py-0.5 rounded text-[10px] font-mono tracking-widest uppercase border"
            :class="categoryStyle[post.category] ?? categoryStyle.default"
          >
            {{ post.category }}
          </span>
          <time class="text-xs font-mono text-slate-500">
            {{ formatDate(post.createdAt) }}
          </time>
        </div>

        <!-- Title -->
        <h1 class="text-3xl md:text-4xl font-bold leading-tight mb-4
                   bg-gradient-to-br from-white to-blue-200
                   bg-clip-text text-transparent text-glow">
          {{ post.title }}
        </h1>

        <!-- Summary -->
        <p class="text-slate-400 text-base leading-relaxed mb-10 border-l-2
                  border-blue-600/50 pl-4 italic">
          {{ post.summary }}
        </p>

        <!-- Divider -->
        <div class="h-px bg-gradient-to-r from-blue-800/60 via-blue-500/30 to-transparent mb-10" />

        <!-- Markdown body -->
        <div class="prose-scifi" v-html="renderedContent" />

      </article>
    </template>

  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { marked } from 'marked'
import { usePostsStore } from '../stores/posts'

const route = useRoute()
const store = usePostsStore()
const post  = computed(() => store.current)

const renderedContent = computed(() =>
  post.value?.content ? marked.parse(post.value.content) : ''
)

const categoryStyle = {
  Tech:    'bg-blue-500/15 text-blue-300 border-blue-500/30',
  Essay:   'bg-purple-500/15 text-purple-300 border-purple-500/30',
  Media:   'bg-cyan-500/15 text-cyan-300 border-cyan-500/30',
  default: 'bg-slate-500/15 text-slate-300 border-slate-500/30',
}

function formatDate(iso) {
  if (!iso) return ''
  return new Date(iso).toLocaleDateString('zh-CN', {
    year: 'numeric', month: 'long', day: 'numeric',
  })
}

onMounted(() => store.loadPost(route.params.id))
</script>

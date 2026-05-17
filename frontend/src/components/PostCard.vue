<template>
  <RouterLink :to="`/posts/${post.id}`" class="block group">
    <article
      class="card-glass h-full flex flex-col cursor-pointer"
      :style="{ animationDelay: `${index * 80}ms` }"
      :class="visible ? 'animate-fade-slide-up' : 'opacity-0'"
    >
      <!-- Mood gradient strip -->
      <div
        class="h-1 w-full flex-shrink-0"
        :style="{ background: post.moodGradient || defaultGradient }"
      />

      <!-- Card body -->
      <div class="flex flex-col flex-1 p-5 gap-3">

        <!-- Meta row -->
        <div class="flex items-center justify-between">
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
        <h2 class="text-base font-semibold text-slate-100 leading-snug
                   group-hover:text-blue-300 transition-colors line-clamp-2">
          {{ post.title }}
        </h2>

        <!-- Summary -->
        <p class="text-sm text-slate-400 leading-relaxed line-clamp-3 flex-1">
          {{ post.summary }}
        </p>

        <!-- Read more -->
        <div class="flex items-center gap-1 text-xs font-mono text-blue-500
                    group-hover:text-cyan-400 transition-colors mt-1">
          <span>READ</span>
          <span class="translate-x-0 group-hover:translate-x-1 transition-transform">→</span>
        </div>

      </div>
    </article>
  </RouterLink>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  post:  { type: Object, required: true },
  index: { type: Number, default: 0 },
})

const visible = ref(false)
onMounted(() => {
  // staggered entrance
  setTimeout(() => { visible.value = true }, props.index * 80)
})

const defaultGradient = 'linear-gradient(90deg, #1e3a8a, #1e40af)'

const categoryStyle = {
  Tech:    'bg-blue-500/15 text-blue-300 border-blue-500/30',
  Essay:   'bg-purple-500/15 text-purple-300 border-purple-500/30',
  Media:   'bg-cyan-500/15 text-cyan-300 border-cyan-500/30',
  default: 'bg-slate-500/15 text-slate-300 border-slate-500/30',
}

function formatDate(iso) {
  if (!iso) return ''
  const d = new Date(iso)
  return d.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
}
</script>

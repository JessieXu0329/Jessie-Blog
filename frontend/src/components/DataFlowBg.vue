<template>
  <div class="fixed inset-0 overflow-hidden pointer-events-none z-0" aria-hidden="true">
    <!-- Base dark background -->
    <div class="absolute inset-0 bg-space-950" />

    <!-- Radial ambient glow — top center -->
    <div class="absolute -top-40 left-1/2 -translate-x-1/2
                w-[900px] h-[420px] rounded-full blur-3xl
                bg-blue-700/10" />
    <!-- Bottom-right accent glow -->
    <div class="absolute -bottom-20 -right-20
                w-[500px] h-[500px] rounded-full blur-3xl
                bg-cyan-800/8" />

    <!-- Dot grid overlay -->
    <div class="absolute inset-0 opacity-[0.07]"
         :style="{ backgroundImage: dotGrid, backgroundSize: '28px 28px' }" />

    <!-- Vertical data streams -->
    <div
      v-for="s in streams"
      :key="s.id"
      class="absolute top-0 w-px"
      :style="{
        left:      s.left,
        height:    s.height,
        background: s.gradient,
        animation: `flowDown ${s.duration} linear ${s.delay} infinite`,
      }"
    />

    <!-- Horizontal scan line -->
    <div
      class="absolute left-0 right-0 h-px
             bg-gradient-to-r from-transparent via-blue-400/25 to-transparent
             animate-scan-line"
    />

    <!-- Corner chrome decorations -->
    <div class="absolute top-4 left-4 w-12 h-12 border-t border-l border-blue-500/20 rounded-tl-sm" />
    <div class="absolute top-4 right-4 w-12 h-12 border-t border-r border-blue-500/20 rounded-tr-sm" />
    <div class="absolute bottom-4 left-4 w-12 h-12 border-b border-l border-blue-500/20 rounded-bl-sm" />
    <div class="absolute bottom-4 right-4 w-12 h-12 border-b border-r border-blue-500/20 rounded-br-sm" />
  </div>
</template>

<script setup>
import { computed } from 'vue'

const dotGrid = computed(() => {
  const s = `<svg xmlns='http://www.w3.org/2000/svg' width='28' height='28'>
    <circle cx='1' cy='1' r='0.8' fill='%2338bdf8'/>
  </svg>`
  return `url("data:image/svg+xml,${encodeURIComponent(s)}")`
})

// Deterministic streams — no randomness so SSR / HMR stays stable
const STREAM_COLORS = [
  ['transparent', 'rgba(34,211,238,0.75)',  'transparent'],  // cyan
  ['transparent', 'rgba(59,130,246,0.65)',  'transparent'],  // blue
  ['transparent', 'rgba(99,102,241,0.55)',  'transparent'],  // indigo
]

const streams = Array.from({ length: 18 }, (_, i) => {
  const [a, b, c] = STREAM_COLORS[i % 3]
  const pxH       = 90 + (i % 6) * 35
  const duration  = `${5.5 + (i % 5) * 2.2}s`
  const delay     = `-${(i * 1.37) % 11}s`
  return {
    id:       i,
    left:     `${(i * 5.55 + 1.5) % 99}%`,
    height:   `${pxH}px`,
    gradient: `linear-gradient(to bottom, ${a}, ${b}, ${c})`,
    duration,
    delay,
  }
})
</script>

<style scoped>
@keyframes flowDown {
  0%   { transform: translateY(-150px); opacity: 0; }
  8%   { opacity: 1; }
  92%  { opacity: 1; }
  100% { transform: translateY(calc(100vh + 150px)); opacity: 0; }
}
</style>

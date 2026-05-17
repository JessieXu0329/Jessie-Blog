<template>
  <article
    class="group relative overflow-hidden rounded-xl
           border border-slate-800/50
           bg-slate-900/30 backdrop-blur-sm
           transition-all duration-500 ease-out
           hover:border-sky-500/30
           hover:shadow-[0_0_32px_rgba(56,189,248,0.12)]
           cursor-default"
    :class="sizeClass"
  >
    <!-- Real photo (when available) -->
    <img
      v-if="src"
      :src="src"
      :alt="title"
      class="absolute inset-0 w-full h-full object-cover
             transition-transform duration-700 ease-out
             group-hover:scale-105"
    />

    <!-- Gradient placeholder (fallback when no photo) -->
    <div
      v-else
      class="absolute inset-0 transition-transform duration-700 ease-out
             group-hover:scale-105"
      :class="gradient"
    />

    <!-- Subtle geometric overlay — dot grid -->
    <div
      class="absolute inset-0 opacity-[0.06]"
      :style="{ backgroundImage: dotPattern, backgroundSize: '20px 20px' }"
    />

    <!-- Vignette overlay for depth -->
    <div class="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent" />

    <!-- Thin scan line across the image -->
    <div class="absolute inset-x-0 h-px top-1/3 bg-gradient-to-r from-transparent via-sky-400/8 to-transparent" />

    <!-- Info overlay — slides up slightly on hover -->
    <div
      class="absolute inset-x-0 bottom-0 p-5
             translate-y-1 group-hover:translate-y-0
             transition-transform duration-500 ease-out"
    >
      <!-- Date line -->
      <p class="font-mono text-[10px] tracking-[0.25em] text-sky-400/60 uppercase mb-1.5">
        {{ date }}
      </p>

      <!-- Title -->
      <h3 class="text-lg sm:text-xl font-semibold tracking-wide text-white mb-1">
        {{ title }}
      </h3>

      <!-- Location + description -->
      <div class="flex items-center gap-2 mb-1.5">
        <span class="h-px w-4 bg-sky-500/30" />
        <span class="font-mono text-[10px] tracking-widest text-slate-400 uppercase">
          {{ location }}
        </span>
      </div>

      <p class="text-xs text-slate-400 leading-relaxed opacity-0
                group-hover:opacity-100 transition-opacity duration-500 delay-100">
        {{ description }}
      </p>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  title:       { type: String, required: true },
  location:    { type: String, required: true },
  date:        { type: String, required: true },
  description: { type: String, default: '' },
  gradient:    { type: String, required: true },
  size:        { type: String, default: 'md' },
  src:         { type: String, default: '' },   // real photo URL
})

// Map size prop to grid span classes
const sizeClass = computed(() => {
  const map = {
    sm:   '',
    md:   'lg:col-span-1 lg:row-span-1',
    lg:   'lg:col-span-2 lg:row-span-1',
    tall: 'lg:col-span-1 lg:row-span-2',
  }
  return map[props.size] || map.md
})

// Inline dot grid SVG for the geometric overlay
const dotPattern = computed(() => {
  const svg = `<svg xmlns='http://www.w3.org/2000/svg' width='20' height='20'>
    <circle cx='1' cy='1' r='0.6' fill='%2338bdf8'/>
  </svg>`
  return `url("data:image/svg+xml,${encodeURIComponent(svg)}")`
})
</script>

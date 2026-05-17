<template>
  <RouterLink
    v-if="id"
    :to="`/projects/${id}`"
    class="card-glass p-6 sm:p-8 group animate-fade-slide-up block cursor-pointer"
    :style="animationDelay ? { animationDelay } : {}"
  >
    <!-- Hover indicator -->
    <span class="absolute top-4 right-4 font-mono text-[9px] tracking-wider text-slate-700 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
      VIEW →
    </span>
    <!-- Header: title + status -->
    <div class="flex items-start justify-between gap-4 mb-4">
      <div>
        <h3 class="text-xl sm:text-2xl font-bold tracking-wide text-slate-100 mb-1">
          {{ title }}
        </h3>
        <p class="text-xs font-mono tracking-wider text-sky-400/60 uppercase">
          {{ subtitle }}
        </p>
      </div>
      <span
        class="shrink-0 font-mono text-[10px] tracking-[0.2em] uppercase px-2 py-1 rounded border"
        :class="statusStyle"
      >
        {{ status }}
      </span>
    </div>

    <!-- Description -->
    <p class="text-sm text-slate-400 leading-relaxed mb-5">
      {{ description }}
    </p>

    <!-- Features list (optional) -->
    <ul v-if="features.length" class="mb-5 space-y-1.5">
      <li
        v-for="f in features"
        :key="f"
        class="flex items-start gap-2 text-sm text-slate-300"
      >
        <span class="text-sky-500 mt-0.5 shrink-0">&#x25b8;</span>
        {{ f }}
      </li>
    </ul>

    <!-- Meta row -->
    <div class="flex flex-wrap items-center gap-x-6 gap-y-2 pt-4
                border-t border-slate-800/60">
      <MetaItem v-for="m in meta" :key="m.label" :label="m.label" :value="m.value" />
    </div>
  </RouterLink>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  id:          { type: [Number, String], default: null },
  title:       { type: String, required: true },
  subtitle:    { type: String, default: '' },
  description: { type: String, required: true },
  features:    { type: Array, default: () => [] },
  status:      { type: String, default: 'IN PROGRESS' },
  role:        { type: String, default: '' },
  year:        { type: String, default: '' },
  animationDelay: { type: String, default: '' },
})

const statusStyle = computed(() => {
  if (props.status === 'DEPLOYED') {
    return 'border-emerald-500/30 text-emerald-400 bg-emerald-500/10'
  }
  return 'border-sky-500/30 text-sky-400 bg-sky-500/10'
})

const meta = computed(() => [
  ...(props.role ? [{ label: 'ROLE', value: props.role }] : []),
  ...(props.year ? [{ label: 'YEAR', value: props.year }] : []),
])

// Tiny inline meta item
const MetaItem = {
  props: { label: String, value: String },
  template: `
    <div class="flex items-center gap-2">
      <span class="font-mono text-[10px] tracking-widest text-slate-600 uppercase">{{ label }}</span>
      <span class="font-mono text-xs tracking-wide text-slate-300">{{ value }}</span>
    </div>
  `,
}
</script>

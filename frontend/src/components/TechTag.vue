<template>
  <span
    class="inline-block px-3 py-1 rounded font-mono text-xs tracking-wider
           border transition-all duration-200
           hover:-translate-y-px cursor-default"
    :class="variantStyle"
  >
    {{ name }}
  </span>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  name: { type: String, required: true },
  variant: {
    type: String,
    default: 'default',
    validator: v => ['languages', 'frontend', 'backend', 'ai'].includes(v),
  },
})

const variantStyle = computed(() => {
  const map = {
    languages: 'bg-orange-500/10 border-orange-500/30 text-orange-300',
    frontend:  'bg-emerald-500/10 border-emerald-500/30 text-emerald-300',
    backend:   'bg-sky-500/10 border-sky-500/30 text-sky-300',
    ai:        'bg-violet-500/10 border-violet-500/30 text-violet-300',
    default:   'bg-slate-500/10 border-slate-500/30 text-slate-300',
  }
  return map[props.variant] || map.default
})
</script>

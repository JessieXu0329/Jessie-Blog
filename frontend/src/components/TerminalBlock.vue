<template>
  <div
    class="relative rounded-xl overflow-hidden
           bg-[#020a14] border border-slate-800/60
           shadow-[inset_0_0_40px_rgba(0,0,0,0.4)]"
  >
    <!-- Terminal header bar -->
    <div class="flex items-center gap-1.5 px-4 py-3 border-b border-slate-800/40 bg-slate-900/30">
      <span class="w-2.5 h-2.5 rounded-full bg-red-500/60" />
      <span class="w-2.5 h-2.5 rounded-full bg-yellow-500/60" />
      <span class="w-2.5 h-2.5 rounded-full bg-emerald-500/60" />
      <span class="ml-2 font-mono text-[10px] tracking-wider text-slate-600">
        terminal@jessie:~/
      </span>
    </div>

    <!-- Terminal body -->
    <div class="px-5 py-5 font-mono text-sm leading-7 text-slate-300">
      <p class="text-sky-400/80 mb-4">
        <span class="text-slate-500">&gt;</span> current_status
      </p>

      <template v-for="(group, idx) in sections" :key="idx">
        <p class="text-slate-500 text-xs tracking-wider mb-2">{{ group.label }}:</p>
        <ul class="mb-4 space-y-0.5">
          <li v-for="item in group.items" :key="item" class="flex items-start gap-2">
            <span class="text-sky-500/60 mt-0.5">&bull;</span>
            <span class="text-slate-300">{{ item }}</span>
          </li>
        </ul>
      </template>

      <!-- Cursor line -->
      <p class="flex items-center gap-1.5 text-sky-400">
        <span class="text-slate-500">&gt;</span>
        <span class="inline-block w-2 h-4 bg-sky-400 animate-blink-cursor align-middle" />
      </p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  sections: {
    type: Array,
    required: true,
    // shape: [{ label: String, items: String[] }]
  },
})
</script>

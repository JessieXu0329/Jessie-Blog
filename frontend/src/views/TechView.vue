<template>
  <div class="relative z-10 min-h-screen pt-14">
    <div class="max-w-4xl mx-auto px-6 py-16">

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 1 — HERO
           ════════════════════════════════════════════════════════ -->
      <section class="mb-20 animate-fade-slide-up">
        <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-5">
          [ TECH ARCHIVE ]
        </p>
        <h1 class="text-3xl sm:text-4xl font-bold tracking-widest
                   bg-gradient-to-br from-white to-sky-300
                   bg-clip-text text-transparent mb-4">
          Tech
        </h1>
        <p class="text-slate-400 text-sm sm:text-base leading-relaxed max-w-xl">
          Building with AI, code, and curiosity.<br>
          Documenting experiments, products, and the process of becoming.
        </p>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 2 — TECH STACK
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-20">
        <!-- Section label -->
        <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
          // TECH STACK
        </p>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-8">
          技术栈 · TECHNICAL ARSENAL
        </p>

        <!-- Category groups -->
        <div class="space-y-8">
          <div
            v-for="cat in techCategories"
            :key="cat.label"
            class="animate-fade-slide-up"
            :style="{ animationDelay: cat.delay }"
          >
            <!-- Category header -->
            <div class="flex items-center gap-3 mb-3">
              <span class="h-px w-6 bg-gradient-to-r"
                    :class="cat.accentLine" />
              <span class="font-mono text-xs tracking-[0.2em] uppercase"
                    :class="cat.accentText">
                {{ cat.label }}
              </span>
            </div>

            <!-- Tags -->
            <div class="flex flex-wrap gap-2">
              <TechTag
                v-for="t in cat.tags"
                :key="t.name"
                :name="t.name"
                :variant="cat.variant"
              />
            </div>
          </div>
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 2.5 — TECH STACK DIVIDER
           ════════════════════════════════════════════════════════════ -->
      <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent mb-20" />

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 3 — FEATURED PROJECTS
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-20">
        <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
          // FEATURED
        </p>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-8">
          项目实践 · PROJECT SHOWCASE
        </p>

        <p v-if="loading" class="font-mono text-[10px] tracking-wider text-slate-700 uppercase animate-pulse">
          // LOADING PROJECTS...
        </p>
        <p v-else-if="projects.length === 0" class="font-mono text-xs tracking-wider text-slate-600">
          // NO PROJECTS YET — USE ADMIN PANEL TO ADD
        </p>
        <div v-else class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <ProjectCard
            v-for="p in projects"
            :key="p.title"
            v-bind="p"
          />
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 3.5 — DIVIDER
           ════════════════════════════════════════════════════════════ -->
      <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent mb-20" />

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 4 — BUILDING PHILOSOPHY
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-20 animate-fade-slide-up" style="animation-delay:80ms">
        <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
          // PHILOSOPHY
        </p>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-8">
          开发理念 · BUILDING PHILOSOPHY
        </p>

        <div class="card-glass p-8 sm:p-10 max-w-2xl">
          <div class="text-slate-300 text-sm sm:text-base leading-7 sm:leading-8">
            <p class="mb-3">
              I don't see coding as purely technical work.
            </p>
            <p class="mb-3">
              Digital products are also emotional experiences —
              interfaces shape feelings,
              interaction creates memory,
              and technology quietly changes the way people live.
            </p>
            <p class="text-sky-300/80">
              I'm interested in building products that feel human.
            </p>
          </div>
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 5 — LEARNING TIMELINE
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-20">
        <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
          // TIMELINE
        </p>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-8">
          学习轨迹 · LEARNING RECORD
        </p>

        <!-- Vertical timeline -->
        <div class="relative max-w-xl">
          <!-- Glowing vertical line -->
          <div class="absolute left-[7px] top-2 bottom-2 w-px
                      bg-gradient-to-b from-sky-500/40 via-blue-500/20 to-sky-500/10" />

          <div
            v-for="(item, idx) in timeline"
            :key="item.year"
            class="relative pl-8 pb-10 last:pb-0 animate-fade-slide-up"
            :style="{ animationDelay: `${idx * 120}ms` }"
          >
            <!-- Glow node on the line -->
            <div class="absolute left-0 top-1.5 w-[15px] h-[15px] rounded-full
                        border-2 border-sky-500/50 bg-sky-500/20
                        shadow-[0_0_10px_rgba(56,189,248,0.3)]" />

            <!-- Year badge -->
            <span class="inline-block font-mono text-xs tracking-widest text-sky-400/80 mb-1.5">
              {{ item.year }}
            </span>

            <!-- Description -->
            <p class="text-sm text-slate-300 leading-relaxed">
              {{ item.text }}
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
import TechTag     from '../components/TechTag.vue'
import ProjectCard from '../components/ProjectCard.vue'
import { fetchTechProjects } from '../api/admin'

// ── Tech stack data (static) ─────────────────────────────────────────
const techCategories = [
  { label: 'Languages', variant: 'languages', accentLine: 'from-orange-500/50 to-transparent', accentText: 'text-orange-300/70', delay: '0ms', tags: [{name:'Java'},{name:'Python'},{name:'TypeScript'},{name:'SQL'}] },
  { label: 'Frontend',  variant: 'frontend',  accentLine: 'from-emerald-500/50 to-transparent', accentText: 'text-emerald-300/70', delay: '40ms',  tags: [{name:'Vue 3'},{name:'TailwindCSS'},{name:'Vite'},{name:'WXML / WXSS'}] },
  { label: 'Backend',   variant: 'backend',   accentLine: 'from-sky-500/50 to-transparent',     accentText: 'text-sky-300/70',     delay: '80ms',  tags: [{name:'Django'},{name:'Spring Boot'},{name:'MySQL'}] },
  { label: 'AI-Native Development', variant: 'ai', accentLine: 'from-violet-500/50 to-transparent', accentText: 'text-violet-300/70', delay: '120ms', tags: [{name:'Claude Code'},{name:'Codex'},{name:'Cursor'},{name:'Vibe Coding'}] },
]

// ── Projects — fetched from backend ──────────────────────────────────
const projects = ref([])
const loading  = ref(true)

onMounted(async () => {
  try {
    const data = await fetchTechProjects()
    projects.value = (Array.isArray(data) ? data : []).map(p => ({
      id:             p.id,
      title:          p.title,
      subtitle:       '',
      description:    p.description ?? '',
      features:       (p.techStack ?? '').split(',').map(s => s.trim()).filter(Boolean),
      status:         p.status ?? 'IN_PROGRESS',
      role:           p.role ?? '',
      year:           p.year ?? '',
      animationDelay: '0ms',
    }))
  } catch (err) {
    console.error('[TechView] Failed to fetch projects:', err)
  } finally {
    loading.value = false
  }
})

// ── Timeline data ────────────────────────────────────────────────────
const timeline = [
  {
    year: '2024',
    text: 'Started learning programming. Built first personal projects and discovered the joy of creating with code.',
  },
  {
    year: '2025',
    text: 'Built first deployed Mini Program. Shipped a full-stack educational platform serving real users.',
  },
  {
    year: '2026',
    text: 'Exploring AI-native development and building personal digital spaces. Experimenting with the intersection of AI and human-centered design.',
  },
]

</script>

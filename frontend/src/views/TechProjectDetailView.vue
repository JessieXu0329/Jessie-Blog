<template>
  <div class="relative z-10 min-h-screen pt-14">
    <div class="max-w-3xl mx-auto px-6 py-16">

      <!-- Loading / Error -->
      <template v-if="loading">
        <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-5 animate-pulse">
          [ LOADING... ]
        </p>
      </template>

      <template v-else-if="!project">
        <p class="font-mono text-[10px] tracking-[0.4em] text-red-400/70 uppercase mb-5">
          [ NOT FOUND ]
        </p>
        <h1 class="text-3xl font-bold tracking-widest text-slate-400 mb-6">Project Not Found</h1>
        <RouterLink to="/tech" class="font-mono text-xs text-sky-400 hover:text-sky-300 transition-colors">
          ← BACK TO TECH ARCHIVE
        </RouterLink>
      </template>

      <template v-else>
        <!-- Hero + Screenshot row -->
        <section class="mb-10 animate-fade-slide-up">
          <RouterLink to="/tech" class="inline-block font-mono text-[10px] tracking-[0.3em] text-slate-600 hover:text-slate-400 uppercase mb-5 transition-colors">
            ← TECH ARCHIVE
          </RouterLink>
          <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-4">
            [ PROJECT DETAIL ]
          </p>

          <div class="flex flex-col sm:flex-row gap-6 items-start">
            <!-- Left: title + meta -->
            <div class="flex-1 min-w-0">
              <h1 class="text-3xl sm:text-4xl font-bold tracking-widest
                         bg-gradient-to-br from-white to-sky-300
                         bg-clip-text text-transparent mb-3">
                {{ project.title }}
              </h1>
              <div class="flex flex-wrap items-center gap-3 mb-4">
                <span class="font-mono text-[10px] tracking-[0.2em] uppercase px-2 py-1 rounded border"
                      :class="project.status === 'DEPLOYED'
                        ? 'border-emerald-500/30 text-emerald-400 bg-emerald-500/10'
                        : 'border-sky-500/30 text-sky-400 bg-sky-500/10'">
                  {{ project.status }}
                </span>
                <span class="font-mono text-xs tracking-wide text-slate-400">{{ project.role }}</span>
                <span class="font-mono text-xs tracking-wide text-slate-600">{{ project.year }}</span>
              </div>
            </div>

            <!-- Right: screenshot thumbnail -->
            <img v-if="project.screenshotUrl" :src="project.screenshotUrl" :alt="project.title"
                 class="w-40 sm:w-48 shrink-0 rounded-xl border border-slate-800/60 shadow-[0_0_24px_rgba(0,0,0,0.5)] hover:scale-105 transition-transform duration-300" />
          </div>
        </section>

        <!-- Description -->
        <section class="mb-10 animate-fade-slide-up" style="animation-delay:100ms">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
            // DESCRIPTION
          </p>
          <div class="card-glass p-6 sm:p-8">
            <p class="text-slate-300 text-sm sm:text-base leading-7 whitespace-pre-line">
              {{ project.description }}
            </p>
          </div>
        </section>

        <!-- Tech Stack -->
        <section v-if="project.techStack" class="mb-10 animate-fade-slide-up" style="animation-delay:140ms">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
            // TECH STACK
          </p>
          <div class="flex flex-wrap gap-2">
            <span v-for="t in project.techStack.split(',').map(s => s.trim()).filter(Boolean)"
                  :key="t"
                  class="px-3 py-1 rounded font-mono text-xs tracking-wider border border-sky-500/20 text-sky-300 bg-sky-500/10">
              {{ t }}
            </span>
          </div>
        </section>

        <!-- Links -->
        <section v-if="project.githubUrl" class="mb-10 animate-fade-slide-up" style="animation-delay:180ms">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
            // LINKS
          </p>
          <a :href="project.githubUrl" target="_blank" rel="noopener noreferrer"
             class="inline-flex items-center gap-2 px-4 py-2.5 rounded-lg font-mono text-xs tracking-widest uppercase
                    border border-sky-500/20 text-sky-400 bg-sky-500/5
                    hover:border-sky-500/50 hover:bg-sky-500/10
                    transition-all duration-200">
            <span>⬡</span> GitHub Repository
          </a>
        </section>

        <!-- Meta footer -->
        <section class="animate-fade-slide-up" style="animation-delay:220ms">
          <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent mb-5" />
          <div class="flex flex-wrap gap-x-8 gap-y-2">
            <div class="flex items-center gap-2">
              <span class="font-mono text-[10px] tracking-widest text-slate-600 uppercase">ROLE</span>
              <span class="font-mono text-xs tracking-wide text-slate-300">{{ project.role }}</span>
            </div>
            <div class="flex items-center gap-2">
              <span class="font-mono text-[10px] tracking-widest text-slate-600 uppercase">YEAR</span>
              <span class="font-mono text-xs tracking-wide text-slate-300">{{ project.year }}</span>
            </div>
            <div class="flex items-center gap-2">
              <span class="font-mono text-[10px] tracking-widest text-slate-600 uppercase">STATUS</span>
              <span class="font-mono text-xs tracking-wide text-slate-300">{{ project.status }}</span>
            </div>
          </div>
        </section>

        <div class="h-16" />
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { fetchTechProject } from '../api/admin'

const route = useRoute()
const project = ref(null)
const loading  = ref(true)

onMounted(async () => {
  try {
    const id = route.params.id
    const data = await fetchTechProject(id)
    project.value = data
  } catch (err) {
    console.error('[ProjectDetail] Failed:', err)
  } finally {
    loading.value = false
  }
})
</script>

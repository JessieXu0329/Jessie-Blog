<template>
  <div class="relative z-10 min-h-screen pt-14">
    <div class="max-w-3xl mx-auto px-6 py-16">

      <!-- ── Header ────────────────────────────────────────────── -->
      <div class="mb-14 animate-fade-slide-up">
        <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-4">
          [ IDENTITY FILE ]
        </p>

        <!-- Avatar + Name row -->
        <div class="flex items-center gap-6 mb-6">
          <!-- Avatar placeholder -->
          <div class="relative w-20 h-20 rounded-full shrink-0
                      border border-sky-500/30
                      bg-gradient-to-br from-sky-900/60 to-blue-950
                      flex items-center justify-center
                      shadow-[0_0_24px_rgba(56,189,248,0.15)]">
            <span class="text-2xl font-bold font-mono text-sky-400">J</span>
            <!-- Rotating ring -->
            <span class="absolute inset-[-3px] rounded-full border border-dashed
                         border-sky-700/40 animate-[spin_12s_linear_infinite]" />
          </div>

          <div>
            <h1 class="text-4xl font-bold tracking-widest pb-1
                       bg-gradient-to-br from-white to-sky-300
                       bg-clip-text text-transparent">
              Jessie 徐沁芯
            </h1>
            <p class="font-mono text-xs tracking-[0.25em] text-slate-500 uppercase mt-1">
              <!-- 在此填写你的头衔 -->
              Developer &amp; Creator
            </p>
          </div>
        </div>

        <!-- Divider -->
        <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent" />
      </div>

      <!-- ── Bio ───────────────────────────────────────────────── -->
      <section class="mb-12 animate-fade-slide-up" style="animation-delay:80ms">
        <SectionLabel>// BIO</SectionLabel>
        <div class="card-glass p-6 text-slate-300 text-sm leading-7 space-y-3">
          <!-- 在此填写个人简介 -->
          <div class="space-y-3">
            <p>
              Hi, I'm Jessie.<br>
              A data science student and indie developer based in Hangzhou.<br>
              Currently building AI-driven projects, full‑stack applications, and small ideas that feel meaningful.<br>
              Interested in technology, digital experiences, and the connection between people and products.<br>
              Beyond coding, I enjoy music, films, traveling, and collecting moments that make life feel a little more vivid.
            </p>

            <div class="h-px w-16 bg-gradient-to-r from-sky-700/40 to-transparent"></div>

            <p>
              你好，我是 徐沁芯。<br>
              一名来自杭州的数据科学学生，也是一位独立开发者。<br>
              目前正在尝试 AI 驱动项目、全栈应用，以及一些自己觉得"有意思"的小东西。<br>
              关注技术、数字体验，以及人与产品之间微妙的连接。<br>
              除了写代码之外，也喜欢音乐、电影、旅行，还有那些让生活变得更鲜活的瞬间。
            </p>
          </div>
          <p class="text-slate-500 font-mono text-xs">
            // TO BE CONTINUED 持续更新中
          </p>
        </div>
      </section>

      <!-- ── Skills ────────────────────────────────────────────── -->
      <section class="mb-12 animate-fade-slide-up" style="animation-delay:160ms">
        <SectionLabel>// SKILLS</SectionLabel>
        <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase mb-4">
          擅长技术语言 · TECH STACK
        </p>
        <div class="flex flex-wrap gap-2">
          <span
            v-for="skill in SKILLS"
            :key="skill.name"
            class="px-3 py-1 rounded font-mono text-xs tracking-wider border transition-all duration-200
                   hover:-translate-y-px"
            :class="skill.style"
          >
            {{ skill.name }}
          </span>
        </div>
      </section>

      <!-- ── Interests → Navigation ──────────────────────────────── -->
      <section class="mb-12 animate-fade-slide-up" style="animation-delay:240ms">
        <SectionLabel>// EXPLORE</SectionLabel>
        <div class="grid grid-cols-2 sm:grid-cols-4 gap-3">
          <div
            v-for="item in EXPLORE"
            :key="item.label"
            class="card-glass p-4 flex flex-col items-center gap-2 text-center
                   cursor-pointer hover:-translate-y-1 hover:border-sky-500/30
                   hover:shadow-[0_0_16px_rgba(56,189,248,0.12)]
                   transition-all duration-200"
            @click="$router.push(item.to)"
          >
            <span class="text-2xl">{{ item.icon }}</span>
            <span class="font-mono text-[10px] tracking-widest text-slate-400 uppercase">
              {{ item.label }}
            </span>
          </div>
        </div>
      </section>

      <!-- ── Contact ───────────────────────────────────────────── -->
      <section class="animate-fade-slide-up" style="animation-delay:320ms">
        <SectionLabel>// CONNECT</SectionLabel>
        <div class="flex flex-wrap gap-3">
          <a
            v-for="link in CONTACT"
            :key="link.label"
            :href="link.href"
            target="_blank"
            rel="noopener noreferrer"
            class="flex items-center gap-2 px-4 py-2 rounded-lg font-mono text-xs
                   tracking-widest uppercase border border-sky-900/50 text-slate-400
                   hover:border-sky-500/50 hover:text-sky-400
                   hover:shadow-[0_0_12px_rgba(56,189,248,0.15)]
                   transition-all duration-200"
          >
            <span>{{ link.icon }}</span>
            {{ link.label }}
          </a>
        </div>
      </section>

    </div>
  </div>
</template>

<script setup>
// ── Section label helper ─────────────────────────────────────
const SectionLabel = {
  template: `<p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-4">
               <slot />
             </p>`,
}

// ── Skills ───────────────────────────────────────────────────
// 按需修改：name 为技能名，style 为 Tailwind 样式
const SKILLS = [
  { name: 'Java',        style: 'bg-orange-500/10 border-orange-500/30 text-orange-300' },
  { name: 'Spring Boot', style: 'bg-green-500/10  border-green-500/30  text-green-300'  },
  { name: 'Vue 3',       style: 'bg-emerald-500/10 border-emerald-500/30 text-emerald-300' },
  { name: 'TypeScript',  style: 'bg-blue-500/10   border-blue-500/30   text-blue-300'   },
  { name: 'MySQL',       style: 'bg-sky-500/10    border-sky-500/30    text-sky-300'    },
  { name: 'Python',    style: 'bg-cyan-500/10   border-cyan-500/30   text-cyan-300'   },
  { name: 'Django',      style: 'bg-blue-600/10   border-blue-600/30   text-blue-200'   },
  { name: 'Git',         style: 'bg-rose-500/10   border-rose-500/30   text-rose-300'   },
]

// ── Explore ────────────────────────────────────────────────
const EXPLORE = [
  { icon: '⬡', label: 'TECH',   to: '/tech'   },
  { icon: '▹', label: 'ESSAYS', to: '/essays' },
  { icon: '▵', label: 'SPORTS', to: '/sports' },
  { icon: '◈', label: 'MEDIA',  to: '/media'  },
]

// ── Contact ──────────────────────────────────────────────────
// 按需替换 href
const CONTACT = [
  { label: 'GitHub',  icon: '⬡', href: 'https://github.com/JessieXu0329' },
]
</script>

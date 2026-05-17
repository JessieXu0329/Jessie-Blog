<template>
  <div class="relative z-10 min-h-screen pt-14">
    <div class="max-w-6xl mx-auto px-6 py-16">

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 1 — HERO
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-24 animate-fade-slide-up">
        <p class="font-mono text-[10px] tracking-[0.4em] text-sky-500/70 uppercase mb-5">
          [ MEDIA VAULT ]
        </p>
        <h1 class="text-3xl sm:text-4xl font-bold tracking-widest
                   bg-gradient-to-br from-white to-sky-300
                   bg-clip-text text-transparent mb-5">
          Media
        </h1>
        <p class="text-slate-400 text-sm sm:text-base leading-relaxed max-w-xl">
          Sounds that shaped certain seasons.
          Films that left something behind.
        </p>
        <p class="text-slate-500 text-sm leading-relaxed mt-1.5">
          收藏那些曾在某个季节里陪伴我的声音与画面。
        </p>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 2 — MUSIC SHELF
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-24">
        <!-- Section header -->
        <div class="mb-10 animate-fade-slide-up" style="animation-delay:0ms">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
            // MUSIC SHELF
          </p>
          <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase">
            唱片架 · SOUNDTRACKS OF CERTAIN SEASONS
          </p>
        </div>

        <!-- Empty state -->
        <p
          v-if="loadingAlbums"
          class="font-mono text-[10px] tracking-wider text-slate-700 uppercase animate-pulse"
        >
          // LOADING ARCHIVE...
        </p>
        <p
          v-else-if="albums.length === 0"
          class="font-mono text-xs tracking-wider text-slate-600"
        >
          // NO ENTRIES YET — USE ADMIN PANEL TO ADD
        </p>

        <!-- Album grid — 4 cols desktop, 2 cols mobile -->
        <div
          v-else
          class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4
                 gap-x-6 gap-y-10"
        >
          <div
            v-for="(album, idx) in albums"
            :key="album.id"
            class="animate-fade-slide-up"
            :style="{ animationDelay: `${idx * 70}ms` }"
          >
            <MusicShelf
              :title="album.title"
              :subtitle="album.subtitle"
              :cover="album.coverUrl"
              :review="album.review"
            />
          </div>
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 2.5 — DIVIDER
           ════════════════════════════════════════════════════════════ -->
      <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent mb-24" />

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 3 — MOVIE VAULT
           ════════════════════════════════════════════════════════════ -->
      <section class="mb-24">
        <!-- Section header -->
        <div class="mb-10 animate-fade-slide-up" style="animation-delay:0ms">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-3">
            // MOVIE VAULT
          </p>
          <p class="font-mono text-[11px] tracking-[0.25em] text-sky-400/60 uppercase">
            映画库 · FILMS THAT LEFT SOMETHING BEHIND
          </p>
        </div>

        <!-- Empty state -->
        <p
          v-if="loadingFilms"
          class="font-mono text-[10px] tracking-wider text-slate-700 uppercase animate-pulse"
        >
          // SYNCING CATALOG...
        </p>
        <p
          v-else-if="films.length === 0"
          class="font-mono text-xs tracking-wider text-slate-600"
        >
          // NO ENTRIES YET — USE ADMIN PANEL TO ADD
        </p>

        <!-- Poster grid — 4 cols desktop, 2 cols mobile -->
        <div
          v-else
          class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4
                 gap-x-6 gap-y-10"
        >
          <div
            v-for="(film, idx) in films"
            :key="film.id"
            class="animate-fade-slide-up"
            :style="{ animationDelay: `${idx * 80}ms` }"
          >
            <MovieVault
              :title="film.title"
              :subtitle="film.subtitle"
              :cover="film.coverUrl"
              :review="film.review"
            />
          </div>
        </div>
      </section>

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 3.5 — DIVIDER
           ════════════════════════════════════════════════════════════ -->
      <div class="h-px bg-gradient-to-r from-sky-800/60 via-blue-700/20 to-transparent mb-20" />

      <!-- ═══════════════════════════════════════════════════════════
           SECTION 4 — FOOTER NOTE
           ════════════════════════════════════════════════════════════ -->
      <section class="animate-fade-slide-up" style="animation-delay:120ms">
        <div class="max-w-md">
          <p class="font-mono text-[10px] tracking-[0.3em] text-slate-600 uppercase mb-4">
            // VAULT STATUS
          </p>
          <p class="font-mono text-xs leading-6 text-slate-500">
            <span class="text-sky-400/60">&gt;</span>
            <span class="ml-2">catalogue_size:</span>
            <span class="text-slate-300 ml-1">{{ albums.length + films.length }}</span>
            <span class="text-slate-700 ml-2">entries</span>
          </p>
          <p class="font-mono text-xs leading-6 text-slate-500">
            <span class="text-sky-400/60">&gt;</span>
            <span class="ml-2">last_updated:</span>
            <span class="text-slate-300 ml-1">2026.05</span>
          </p>
          <p class="font-mono text-xs leading-6 text-slate-600 mt-3">
            // 持续收集中 · COLLECTION IN PROGRESS
          </p>
        </div>
      </section>

      <!-- Bottom breathing space -->
      <div class="h-16" />

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import MusicShelf from '../components/MusicShelf.vue'
import MovieVault from '../components/MovieVault.vue'
import { fetchMediaEntries } from '../api/admin'

// ── Fetch from Spring Boot backend ─────────────────────────────────
const loadingAlbums = ref(true)
const loadingFilms  = ref(true)
const allEntries    = ref([])

onMounted(async () => {
  try {
    const data = await fetchMediaEntries()
    console.log('[MediaView] Fetched entries:', data)
    allEntries.value = Array.isArray(data) ? data : (data?.data ?? [])
  } catch (err) {
    console.error('[MediaView] Failed to fetch media entries:', err)
  } finally {
    loadingAlbums.value = false
    loadingFilms.value  = false
  }
})

// Split by category
const albums = computed(() =>
  allEntries.value.filter(e => e.category === 'MUSIC')
)

const films = computed(() =>
  allEntries.value.filter(e => e.category === 'MOVIE')
)
</script>

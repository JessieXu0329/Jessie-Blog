<template>
  <!-- Flip wrapper — click to see review -->
  <div
    class="movie-flip-container"
    :class="{ 'is-flipped': flipped }"
    @click="toggle"
  >
    <div class="movie-flip-inner">
      <!-- ── FRONT: Poster ──────────────────────────────────── -->
      <div class="movie-flip-front">
        <div class="movie-vault-wrapper group">
          <div class="movie-vault-frame">
            <div class="movie-vault-poster">
              <img v-if="cover" :src="cover" :alt="title" class="movie-vault-img" />
              <div v-else class="movie-vault-img movie-vault-fallback">
                <span class="font-mono text-4xl font-bold text-slate-700">{{ title.charAt(0) }}</span>
              </div>
              <div class="movie-vault-inset-shadow" />
              <div class="movie-vault-light-spill" />
            </div>
            <div class="movie-vault-frame-glow" />
          </div>
          <p class="movie-vault-label">
            <span class="text-sky-400/70">{{ subtitle }}</span>
            <span class="text-slate-600 mx-1.5">—</span>
            <span class="text-slate-300">{{ title }}</span>
          </p>
          <!-- Flip hint on hover -->
          <p v-if="review" class="text-[9px] font-mono tracking-widest text-slate-700 text-center mt-1 opacity-0 group-hover:opacity-100 transition-opacity cursor-pointer">
            ↻ CLICK TO FLIP
          </p>
        </div>
      </div>

      <!-- ── BACK: Review ───────────────────────────────────── -->
      <div v-if="review" class="movie-flip-back">
        <div class="card-glass p-6 flex flex-col items-center justify-center text-center h-full">
          <p class="font-mono text-[10px] tracking-[0.3em] text-sky-400/60 uppercase mb-4">
            // ONE-LINE REVIEW
          </p>
          <p class="text-slate-300 text-sm sm:text-base leading-relaxed italic">
            {{ review }}
          </p>
          <p class="font-mono text-[10px] tracking-wider text-slate-500 mt-5">
            {{ subtitle }} — {{ title }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

defineProps({
  title:    { type: String, required: true },
  subtitle: { type: String, required: true },
  cover:    { type: String, default: '' },
  review:   { type: String, default: '' },
})

const flipped = ref(false)
function toggle() { flipped.value = !flipped.value }
</script>

<style scoped>
/* ── Flip container ─────────────────────────────────────────── */
.movie-flip-container {
  perspective: 1000px;
  cursor: default;
}
.movie-flip-inner {
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}
.is-flipped .movie-flip-inner {
  transform: rotateY(180deg);
}

/* ── Front / Back faces ────────────────────────────────────── */
.movie-flip-front,
.movie-flip-back {
  backface-visibility: hidden;
}
.movie-flip-back {
  position: absolute;
  inset: 0;
  transform: rotateY(180deg);
}

/* ── Existing poster styles ─────────────────────────────────── */
.movie-vault-wrapper {
  display: flex;
  flex-direction: column;
}
.movie-vault-frame {
  position: relative;
  width: 100%;
  aspect-ratio: 2 / 3;
  border-radius: 6px;
  overflow: hidden;
  transition: transform 0.5s cubic-bezier(0.23, 1, 0.32, 1),
              box-shadow 0.5s ease;
}
.movie-flip-front:hover .movie-vault-frame {
  transform: translateY(-5px);
}
.movie-vault-poster {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.movie-vault-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  transition: transform 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}
.movie-flip-front:hover .movie-vault-img {
  transform: scale(1.03);
}
.movie-vault-fallback {
  background: linear-gradient(160deg, #0f172a 0%, #1e293b 50%, #0f172a 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.movie-vault-inset-shadow {
  position: absolute;
  inset: 0;
  border-radius: 6px;
  box-shadow:
    inset 0 0 60px rgba(0, 0, 0, 0.7),
    inset 0 0 8px rgba(0, 0, 0, 0.4),
    inset 0 -2px 6px rgba(56, 189, 248, 0.04),
    inset 0 2px 6px rgba(56, 189, 248, 0.03);
  pointer-events: none;
}
.movie-vault-light-spill {
  position: absolute;
  top: 0;
  left: 10%;
  right: 10%;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(56,189,248,0.3), rgba(147,197,253,0.15), rgba(56,189,248,0.3), transparent);
  box-shadow: 0 0 12px rgba(56, 189, 248, 0.2);
  opacity: 0.5;
  transition: opacity 0.5s ease;
  pointer-events: none;
}
.movie-flip-front:hover .movie-vault-light-spill {
  opacity: 1;
}
.movie-vault-frame-glow {
  position: absolute;
  inset: 0;
  border-radius: 6px;
  border: 1px solid rgba(56, 189, 248, 0.06);
  box-shadow: 0 0 0 1px rgba(15,23,42,0.5), inset 0 0 20px rgba(56,189,248,0.03);
  pointer-events: none;
  transition: border-color 0.5s ease, box-shadow 0.5s ease;
}
.movie-flip-front:hover .movie-vault-frame-glow {
  border-color: rgba(56, 189, 248, 0.2);
  box-shadow: 0 0 0 1px rgba(15,23,42,0.5), inset 0 0 30px rgba(56,189,248,0.06);
}
.movie-vault-label {
  margin-top: 14px;
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.65rem;
  letter-spacing: 0.12em;
  line-height: 1.6;
  text-align: center;
}
</style>

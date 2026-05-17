<template>
  <!-- Flip wrapper — click to see review -->
  <div
    class="music-flip-container"
    :class="{ 'is-flipped': flipped }"
    @click="toggle"
  >
    <div class="music-flip-inner">
      <!-- ── FRONT: CD case ─────────────────────────────────── -->
      <div class="music-flip-front">
        <div class="music-shelf-perspective group">
          <div class="music-shelf-case">
            <div class="music-shelf-face">
              <img v-if="cover" :src="cover" :alt="title" class="music-shelf-cover" />
              <div v-else class="music-shelf-cover music-shelf-fallback">
                <span class="font-mono text-3xl font-bold text-slate-700">{{ title.charAt(0) }}</span>
              </div>
              <div class="music-shelf-reflection" />
            </div>
            <div class="music-shelf-spine">
              <div class="flex flex-col justify-center items-center h-full px-1">
                <span class="font-mono text-[6px] tracking-[0.15em] text-slate-500/60 uppercase"
                      style="writing-mode: vertical-rl;">{{ title }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Label -->
        <p class="music-shelf-label">
          <span class="text-sky-400/70">{{ subtitle }}</span>
          <span class="text-slate-600 mx-1.5">—</span>
          <span class="text-slate-400">{{ title }}</span>
        </p>

        <!-- Flip hint -->
        <p v-if="review" class="text-[9px] font-mono tracking-widest text-slate-700 text-center mt-1 cursor-pointer">
          ↻ CLICK TO FLIP
        </p>
      </div>

      <!-- ── BACK: Review ───────────────────────────────────── -->
      <div v-if="review" class="music-flip-back">
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
.music-flip-container {
  perspective: 1000px;
  cursor: default;
}
.music-flip-inner {
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}
.is-flipped .music-flip-inner {
  transform: rotateY(180deg);
}

/* ── Front / Back faces ────────────────────────────────────── */
.music-flip-front,
.music-flip-back {
  backface-visibility: hidden;
}
.music-flip-back {
  position: absolute;
  inset: 0;
  transform: rotateY(180deg);
}

/* ── Existing CD case styles ────────────────────────────────── */
.music-shelf-perspective {
  perspective: 900px;
  perspective-origin: center center;
}
.music-shelf-case {
  position: relative;
  width: 100%;
  aspect-ratio: 1 / 1;
  transform-style: preserve-3d;
  transform: rotateY(30deg);
  transition: transform 0.7s cubic-bezier(0.23, 1, 0.32, 1),
              filter 0.5s ease;
  will-change: transform;
}
.music-flip-front:hover .music-shelf-case {
  transform: rotateY(4deg);
}
.music-shelf-face {
  position: absolute;
  inset: 0;
  backface-visibility: hidden;
  border-radius: 4px;
  overflow: hidden;
  border: 1px solid rgba(56, 189, 248, 0.08);
  transition: border-color 0.5s ease,
              box-shadow 0.5s ease;
}
.music-flip-front:hover .music-shelf-face {
  border-color: rgba(56, 189, 248, 0.35);
  box-shadow:
    0 0 24px rgba(56, 189, 248, 0.18),
    0 0 48px rgba(56, 189, 248, 0.06),
    inset 0 0 24px rgba(56, 189, 248, 0.04);
}
.music-shelf-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}
.music-shelf-fallback {
  background: linear-gradient(135deg, #0f172a 0%, #1e293b 50%, #0f172a 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.music-shelf-reflection {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, transparent 40%, rgba(255,255,255,0.03) 50%, transparent 60%);
  pointer-events: none;
}
.music-shelf-spine {
  position: absolute;
  top: 0;
  right: 0;
  width: 10px;
  height: 100%;
  transform: rotateY(90deg) translateZ(5px);
  transform-origin: right center;
  backface-visibility: hidden;
  border-radius: 0 2px 2px 0;
  background: linear-gradient(to right, rgba(15,23,42,0.95), rgba(30,41,59,0.8), rgba(15,23,42,0.95));
  border: 1px solid rgba(255,255,255,0.03);
  pointer-events: none;
}
.music-shelf-label {
  margin-top: 14px;
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.65rem;
  letter-spacing: 0.12em;
  line-height: 1.6;
  text-align: center;
  transition: opacity 0.4s ease;
}
</style>

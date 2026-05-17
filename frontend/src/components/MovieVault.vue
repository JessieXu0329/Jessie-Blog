<template>
  <div class="movie-vault-wrapper group">
    <!-- Lightbox frame — poster embedded in dark wall -->
    <div class="movie-vault-frame">
      <!-- Poster image -->
      <div class="movie-vault-poster">
        <img
          v-if="cover"
          :src="cover"
          :alt="title"
          class="movie-vault-img"
        />
        <!-- Fallback when no poster image -->
        <div v-else class="movie-vault-img movie-vault-fallback">
          <span class="font-mono text-4xl font-bold text-slate-700">{{ title.charAt(0) }}</span>
        </div>

        <!-- Inner shadow overlay — embedded depth -->
        <div class="movie-vault-inset-shadow" />

        <!-- Top light strip — lightbox spill -->
        <div class="movie-vault-light-spill" />
      </div>

      <!-- Lightbox frame border glow -->
      <div class="movie-vault-frame-glow" />
    </div>

    <!-- Label: Director — Title -->
    <p class="movie-vault-label">
      <span class="text-sky-400/70">{{ subtitle }}</span>
      <span class="text-slate-600 mx-1.5">—</span>
      <span class="text-slate-300">{{ title }}</span>
    </p>

    <!-- One-line review — slides up on hover with frosted glass -->
    <div class="movie-vault-review">
      <p>{{ review }}</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title:    { type: String, required: true },
  subtitle: { type: String, required: true }, // director
  cover:    { type: String, default: '' },
  review:   { type: String, default: '' },   // one-line comment
})
</script>

<style scoped>
/* ── Wrapper ────────────────────────────────────────────────── */
.movie-vault-wrapper {
  display: flex;
  flex-direction: column;
}

/* ── Lightbox frame ─────────────────────────────────────────── */
.movie-vault-frame {
  position: relative;
  width: 100%;
  aspect-ratio: 2 / 3; /* classic vertical poster ratio */
  border-radius: 6px;
  overflow: hidden;
  transition: transform 0.5s cubic-bezier(0.23, 1, 0.32, 1),
              box-shadow 0.5s ease;
}

/* Hover: subtle lift */
.movie-vault-wrapper:hover .movie-vault-frame {
  transform: translateY(-5px);
}

/* ── Poster container ───────────────────────────────────────── */
.movie-vault-poster {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

/* ── Poster image ───────────────────────────────────────────── */
.movie-vault-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  transition: transform 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}

.movie-vault-wrapper:hover .movie-vault-img {
  transform: scale(1.03);
}

/* Fallback gradient when no poster */
.movie-vault-fallback {
  background: linear-gradient(160deg, #0f172a 0%, #1e293b 50%, #0f172a 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

/* ── Inset shadow — embedded in dark wall ───────────────────── */
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

/* ── Light spill from top — lightbox glow ───────────────────── */
.movie-vault-light-spill {
  position: absolute;
  top: 0;
  left: 10%;
  right: 10%;
  height: 1px;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(56, 189, 248, 0.3),
    rgba(147, 197, 253, 0.15),
    rgba(56, 189, 248, 0.3),
    transparent
  );
  box-shadow: 0 0 12px rgba(56, 189, 248, 0.2);
  opacity: 0.5;
  transition: opacity 0.5s ease;
  pointer-events: none;
}

.movie-vault-wrapper:hover .movie-vault-light-spill {
  opacity: 1;
}

/* ── Frame border glow ──────────────────────────────────────── */
.movie-vault-frame-glow {
  position: absolute;
  inset: 0;
  border-radius: 6px;
  border: 1px solid rgba(56, 189, 248, 0.06);
  box-shadow:
    0 0 0 1px rgba(15, 23, 42, 0.5),
    inset 0 0 20px rgba(56, 189, 248, 0.03);
  pointer-events: none;
  transition: border-color 0.5s ease,
              box-shadow 0.5s ease;
}

.movie-vault-wrapper:hover .movie-vault-frame-glow {
  border-color: rgba(56, 189, 248, 0.2);
  box-shadow:
    0 0 0 1px rgba(15, 23, 42, 0.5),
    inset 0 0 30px rgba(56, 189, 248, 0.06);
}

/* ── Bottom label ───────────────────────────────────────────── */
.movie-vault-label {
  margin-top: 14px;
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.65rem;
  letter-spacing: 0.12em;
  line-height: 1.6;
  text-align: center;
}

/* ── One-line review — frosted glass ────────────────────────── */
.movie-vault-review {
  margin-top: 6px;
  text-align: center;
  opacity: 0;
  transform: translateY(-4px);
  transition: opacity 0.45s ease,
              transform 0.45s cubic-bezier(0.23, 1, 0.32, 1);
}

.movie-vault-wrapper:hover .movie-vault-review {
  opacity: 1;
  transform: translateY(0);
}

.movie-vault-review p {
  display: inline-block;
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 0.6rem;
  letter-spacing: 0.08em;
  color: rgb(148 163 184); /* slate-400 */
  background: rgba(15, 23, 42, 0.65);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  border: 1px solid rgba(56, 189, 248, 0.1);
  border-radius: 4px;
  padding: 4px 10px;
  line-height: 1.5;
}
</style>

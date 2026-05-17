<template>
  <!-- Persistent sci-fi background -->
  <DataFlowBg />

  <!-- Navigation -->
  <NavBar />

  <!-- Page content (above background layer) -->
  <RouterView v-slot="{ Component }">
    <Transition name="page" mode="out-in">
      <component :is="Component" />
    </Transition>
  </RouterView>
</template>

<script setup>
import DataFlowBg from './components/DataFlowBg.vue'
import NavBar     from './components/NavBar.vue'
</script>

<style>
.page-enter-active,
.page-leave-active {
  transition: opacity 0.25s ease, transform 0.25s ease;
}
.page-enter-from {
  opacity: 0;
  transform: translateY(10px);
}
.page-leave-to {
  opacity: 0;
  transform: translateY(-6px);
}
</style>

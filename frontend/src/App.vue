<template>
  <div id="app">
    <Header />
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
export default {
  name: 'App',
  components: { Header }
}
</script>

<style>
#app {
  min-height: 100vh;
  background: #f0f2f5;
}
.main-content {
  min-height: calc(100vh - 60px);
}
</style>

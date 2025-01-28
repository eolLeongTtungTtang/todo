<template>
  <v-app>
    <v-main>
      <the-header />
      <div class="container">
      <router-view />
    </div>
    </v-main>
  </v-app>
</template>

<script setup>
import TheHeader from "./layouts/TheHeader.vue";
</script>

<style lang="scss">
body {
  font-family: "Pretendard-Regular", sans-serif;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

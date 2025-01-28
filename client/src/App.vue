<template>
  <v-app>
    <v-main>
      <the-header />
      <router-view />
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
</style>

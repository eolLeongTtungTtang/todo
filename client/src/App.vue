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
  font-size: 17px;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 12px 0 12px;
  width: 100%;
}
</style>

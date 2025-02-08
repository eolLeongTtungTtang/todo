<template>
  <v-app>
    <v-main>
      <the-header />
      <div class="container">
        <router-view :key="route.fullPath" />
      </div>
      <teleport to="body">
        <v-alert
          v-if="returnMessage"
          class="messageAlert"
          :text="returnMessage"
          :type="messageType"
          :style="{ bottom: alertLocation }"
          variant="tonal"
        ></v-alert>
      </teleport>
    </v-main>
  </v-app>
</template>

<script setup>
import TheHeader from "./layouts/TheHeader.vue";
import { alertSettings } from "./config/pageSettings";
import { useRoute } from "vue-router";
import { ref, provide, computed } from "vue";

const route = useRoute();

const returnMessage = ref("");
const messageType = ref("");

const setMessage = (message, type) => {
  returnMessage.value = message;
  messageType.value = type;

  setTimeout(() => {
    returnMessage.value = "";
    messageType.value = "";
  }, 2000);
};

provide("setMessage", setMessage);

const alertLocation = computed(() => {
  return alertSettings[route.path].locate;
});
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
  height: 80vh;
}

.messageAlert {
  position: fixed;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
  width: 80%;
}
</style>

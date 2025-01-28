<template>
  <v-container class="headContainer" :style="{ backgroundColor: headerColor }">
    <v-system-bar :style="{ backgroundColor: headerColor }">
      <div class="timeDiv">
        <span class="ms-2"
          >{{ formattedHour }}:{{ formattedMinutes }}{{ period }}</span
        >
      </div>

      <div>
        <v-icon icon="mdi-wifi-strength-4"></v-icon>
        <v-icon icon="mdi-signal" class="ms-2"></v-icon>
        <v-icon icon="mdi-battery" class="ms-2"></v-icon>
      </div>
    </v-system-bar>

    <div class="headerContent">
      <template v-if="route.path === '/'">
        <h2>ToDolist</h2>
      </template>
      <template v-else>
        <v-btn
          class="backBtn"
          icon="mdi-chevron-left"
          variant="plain"
          @click="goBack"
        />
        <h4>
          {{ headerTitle }}
        </h4>
      </template>

      <v-btn
        :style="{
          visibility: route.path !== '/tags' ? 'visible' : 'hidden',
        }"
        class="addBtn"
        :icon="headerBtnIcon"
        elevation="0"
        size="x-large"
      ></v-btn>
    </div>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";
import { useRoute } from "vue-router";
import { headerSettings } from "@/config/pageSettings";

// 뒤로가기
const goBack = () => window.history.back();

let route = useRoute();
// 페이지 별 헤더 아이콘 및 배경색 설정
let headerColor = computed(() => {
  return headerSettings[route.path]?.color || "#b9e192";
});

const headerTitle = computed(() => {
  return headerSettings[route.path]?.title || "";
});

const headerBtnIcon = computed(() => {
  return headerSettings[route.path]?.icon || "mdi-plus-circle-outline";
});

// 현재 시간 설정
const now = ref(new Date());
const formattedHour = computed(() => {
  const hours = now.value.getHours();
  return String(hours > 12 ? hours - 12 : hours || 12).padStart(2, "0");
});
const formattedMinutes = computed(() =>
  String(now.value.getMinutes()).padStart(2, "0")
);
const period = computed(() => (now.value.getHours() >= 12 ? "PM" : "AM"));

const updateTime = () => {
  now.value = new Date();
};

let timer = null;
onMounted(() => {
  updateTime();
  timer = setInterval(updateTime, 1000);
});

onUnmounted(() => {
  clearInterval(timer);
});
</script>

<style scoped>
h2 {
  font-weight: normal;
}

.v-system-bar {
  display: flex;
  justify-content: space-between;
  padding: 7px 10px 0 10px;
}

.timeDiv {
  font-weight: bolder;
  opacity: 0.8;
}

.headContainer {
  padding-left: 30px;
  padding-bottom: 10px;
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.headerContent {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.addBtn.v-btn--density-default,
.addBtn.v-btn--icon.v-btn--density-default {
  width: 22px;
  height: 21px;
  margin: 8px;
  padding: 0 !important;
  min-width: 0 !important;
  display: flex;
  justify-content: center;
  align-items: center;
}

.backBtn {
  width: 10px !important;
  height: 10px !important;
  font-size: large;
  display: flex;
  justify-content: center;
}
</style>

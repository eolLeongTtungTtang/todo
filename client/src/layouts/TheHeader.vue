<template>
  <v-container class="headContainer">
    <v-system-bar>
      <v-icon icon="mdi-wifi-strength-4"></v-icon>
      <v-icon icon="mdi-signal" class="ms-2"></v-icon>
      <v-icon icon="mdi-battery" class="ms-2"></v-icon>

      <span class="ms-2"
        >{{ formattedHour }}:{{ formattedMinutes }}{{ period }}</span
      >
    </v-system-bar>

    <h2>ToDolist</h2>
    <v-btn
      density="compact"
      icon="mdi-plus-circle"
      elevation="0"
      size="x-large"
    ></v-btn>
  </v-container>
</template>

<script setup>
const now = new Date();

// 24시간 형식을 12시간 형식으로 변환
let hour = now.getHours();
const minutes = now.getMinutes();
const period = hour >= 12 ? "PM" : "AM";

if (hour > 12) {
  hour -= 12; // 13시부터는 12시간 형식으로 변환
} else if (hour === 0) {
  hour = 12; // 0시는 12로 표시
}

// 두 자리로 포맷팅
const formattedHour = String(hour).padStart(2, "0");
const formattedMinutes = String(minutes).padStart(2, "0");
</script>

<style scoped>
h2 {
  font-weight: normal;
}

.headContainer {
  padding-right: 1px;
  padding-bottom: 35px;
  height: 130px;
  width: 350px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
</style>

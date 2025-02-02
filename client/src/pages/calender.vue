<template>
  <v-locale-provider locale="ko">
    <v-date-picker
      class="datePicker"
      v-model="selectedDate"
      hide-header
    ></v-date-picker>
  </v-locale-provider>

  <base-divider thickness="1" dividerMargin="0" />

  <v-container class="todoContainer">
    <base-todolist />
  </v-container>
</template>

<script setup>
import BaseDivider from "@/components/ui/BaseDivider.vue";
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import { ref } from "vue";

const selectedDate = ref(new Date());
</script>

<style scoped>
.datePicker {
  padding-top: 0;
  padding-bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.datePickerContainer {
  padding-top: 0;
  padding-bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.todoContainer {
  padding-top: 5px;
}
</style>

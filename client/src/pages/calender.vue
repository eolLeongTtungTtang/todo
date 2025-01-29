<template>
  <v-container class="datePickerContainer">
    <v-locale-provider locale="ko">
      <v-date-picker hide-header></v-date-picker>
    </v-locale-provider>
  </v-container>

  <base-divider thickness="1" dividerMargin="0" />

  <v-container class="todoContainer">
    <base-todolist></base-todolist>
  </v-container>
</template>

<script setup>
import BaseDivider from "@/components/ui/BaseDivider.vue";
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import { ref, computed } from "vue";
</script>

<style scoped>
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

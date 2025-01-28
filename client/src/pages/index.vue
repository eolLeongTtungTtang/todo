<template>
  <the-header>
    <slot></slot>
  </the-header>
  <div class="container">
    <todo-summary />

    <base-divider />

    <!-- todo list -->
    <v-container
      fluid
      class="pa-2 pl-3 d-flex justify-center"
      style="height: 280px; width: 350px"
    >
      <v-row class="pa-2 pl-3 w-100">
        <v-col
          class="pa-0"
          style="height: 100%; max-height: 100%; overflow-y: auto"
        >
          <v-checkbox
            density="comfortable"
            v-for="(item, index) in items"
            :key="index"
            hide-details="false"
            :label="item"
          />
        </v-col>
      </v-row>
    </v-container>

    <base-divider />

    <!-- move to button menu -->
    <calender-button />
    <comp-and-tag-button />
  </div>
</template>

<script setup>
import TheHeader from "@/layouts/TheHeader.vue";
import BaseDivider from "@/components/ui/BaseDivider.vue";
import CalenderButton from "@/components/CalenderButton.vue";
import CompAndTagButton from "@/components/CompAndTagButton.vue";
import TodoSummary from "@/components/TodoSummary.vue";
import { reactive } from "vue";

const items = reactive([
  "봄이 산책시키기",
  "여름이 수영하기",
  "가을이 하이킹",
  "겨울이 스키타기",
  "봄이 산책시키기",
  "여름이 수영하기",
  "가을이 하이킹",
  "겨울이 스키타기",
]);
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

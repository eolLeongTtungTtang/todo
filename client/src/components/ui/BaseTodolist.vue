<template>
  <v-container
    fluid
    class="pa-2 pl-3 d-flex justify-center"
    :style="{ height: listHeight, width: '350px' }"
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
</template>

<script setup>
import { reactive } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

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

const heightPerPage = {
  "/": "280px",
  "/all-todos": "280px",
  "/calender": "280px",
  "/completed": "280px",
  "/tags": "280px",
};

let listHeight = heightPerPage[route.path];
</script>

<style></style>

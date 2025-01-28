<template>
  <v-container
    fluid
    class="pa-2 pl-3 d-flex justify-center"
    :style="{ height: listHeight, width: '350px' }"
  >
    <template v-if="items.length !== 0">
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
    </template>
    <template v-else>
      <div>
        <span>{{ emptyText }}한 항목이 없습니다.</span>
        <v-icon icon="sticker-remove-outline"></v-icon>
      </div>
    </template>
  </v-container>
</template>

<script setup>
import { reactive, computed } from "vue";
import { useRoute } from "vue-router";
import { todoListSettings } from "@/config/pageSettings";

// const items = reactive([
//   "봄이 산책시키기",
//   "여름이 수영하기",
//   "가을이 하이킹",
//   "겨울이 스키타기",
//   "봄이 산책시키기",
//   "여름이 수영하기",
//   "가을이 하이킹",
//   "겨울이 스키타기",
// ]);
const items = reactive([]);

const route = useRoute();
// 페이지 별 투두 리스트 height
const listHeight = computed(() => todoListSettings[route.path]?.todoListHeight);

const emptyText = computed(
  () => todoListSettings[route.path]?.emptyText || "등록"
);
</script>

<style></style>

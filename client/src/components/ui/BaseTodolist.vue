<template>
  <v-container
    v-if="items.length !== 0"
    fluid
    class="d-flex justify-center"
    :style="{ height: listHeight }"
  >
    <v-row class="pa-0">
      <v-col
        class="pa-0"
        style="height: 100%; max-height: 100%; overflow-y: auto"
      >
        <v-checkbox
          class="pr-3"
          density="comfortable"
          v-for="(item, index) in items"
          :key="index"
          hide-details="false"
          :label="item"
        />
      </v-col>
    </v-row>
  </v-container>

  <v-container v-else fluid class="emptyDiv" :style="{ height: listHeight }">
    <span>{{ emptyText }}한 항목이 없습니다.</span>
  </v-container>
</template>

<script setup>
import { reactive, computed, defineEmits } from "vue";
import { useRoute } from "vue-router";
import { todoListSettings } from "@/config/pageSettings";

const items = reactive([
  "봄이 산책시키기",
  "여름이 수영하기",
  "가을이 하이킹",
  "겨울이 스키타기",
  "봄이 산책시키기",
  "여름이 수영하기",
  "가을이 하이킹",
  "겨울이 스키타기",
  "겨울이 스키타기",
  "겨울이 스키타기",
  "겨울이 스키타기",
  "겨울이 스키타기",
  "겨울이 스키타기",
]);
// const items = reactive([]);

const emit = defineEmits(["todoExist"]);
emit("todoExist", items.length > 0);

const route = useRoute();
// 페이지 별 투두 리스트 height
const listHeight = computed(() => todoListSettings[route.path]?.todoListHeight);

const emptyText = computed(
  () => todoListSettings[route.path]?.emptyText || "등록"
);
</script>

<style scoped>
.emptyDiv {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
</style>

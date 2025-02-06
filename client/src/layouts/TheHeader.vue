<template>
  <v-container class="headContainer" :style="{ backgroundColor: headerColor }">
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
        @click="todoModal = true"
        :style="{
          visibility: addbtnShow,
        }"
        class="addBtn"
        :icon="headerBtnIcon"
        elevation="0"
        size="x-large"
      ></v-btn>

      <base-todo-modal
        v-model="todoModal"
        mode="add"
        @close="closeTodoModal"
      ></base-todo-modal>
    </div>
  </v-container>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { headerSettings } from "@/config/pageSettings";
import BaseTodoModal from "@/components/ui/BaseTodoModal.vue";

let route = useRoute();
let router = useRouter();
const todoModal = ref(false);

// 모달 닫기
const closeTodoModal = () => {
  todoModal.value = false;
};

// 뒤로가기
const goBack = () => {
  router.go(-1);
};

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

const addbtnShow = computed(() => {
  return route.path === "/tags" || route.path === "/completed"
    ? "hidden"
    : "visible";
});
</script>

<style scoped>
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
  height: 124px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

@media (min-width: 1280px) {
  .v-container {
    max-width: 100% !important;
  }
}

.headerContent {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.backBtn {
  width: 10px !important;
  height: 10px !important;
  font-size: large;
  display: flex;
  justify-content: center;
}
</style>

<template>
  <v-app>
    <v-main>
      <the-header />
      <div class="container">
        <router-view
          :reloadDataFlag="reloadDataFlag"
          @update:reloadDataFlag="reloadDataFlag = $event"
          :key="route.fullPath"
        />
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
      <teleport to="body">
        <base-todo-modal
          v-model="openModal"
          class="todoModal"
          :mode="mode"
          :todo="todo"
          @close="closeModal"
          @reload="reloadData(response)"
        ></base-todo-modal>
      </teleport>
    </v-main>
  </v-app>
</template>

<script setup>
import TheHeader from "./layouts/TheHeader.vue";
import BaseTodoModal from "./components/ui/BaseTodoModal.vue";
import { alertSettings } from "./config/pageSettings";
import { useRoute } from "vue-router";
import { ref, provide, computed } from "vue";

const route = useRoute();

const returnMessage = ref("");
const messageType = ref("");
const openModal = ref(false);
const mode = ref("");
const todo = ref({});
const reloadDataFlag = ref(false);

const setMessage = (message, type) => {
  returnMessage.value = message;
  messageType.value = type;

  setTimeout(() => {
    returnMessage.value = "";
    messageType.value = "";
  }, 2000);
};

const todoModal = (open, modalMode, todoValue = {}) => {
  openModal.value = open;
  mode.value = modalMode || "add";
  todo.value = todoValue;
};

// 모달 데이터 초기화 및 emit
const closeModal = () => {
  openModal.value = false;
};

const reloadData = (response) => {
  if (response) {
    const status = response.success ? "success" : "error";
    setMessage(response.message, status);
  }
  reloadDataFlag.value = true;

  closeModal();
};

provide("setMessage", setMessage);
provide("todoModal", todoModal);

const alertLocation = computed(() => {
  return alertSettings[route.path].locate || "23vh";
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

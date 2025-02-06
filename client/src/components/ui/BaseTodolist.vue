<template>
  <v-container
    v-if="todos.length !== 0"
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
          v-for="todo in todos"
          :key="todo.todoId"
          hide-details="false"
          :label="todo.title"
        >
          <template #label>
            <v-label @dblclick="handleDoubleClick(todo)">
              {{ todo.title }}
            </v-label>
          </template>
        </v-checkbox>
      </v-col>
    </v-row>
  </v-container>

  <base-todo-modal
    v-model="todoModal"
    mode="edit"
    @close="closeTodoModal"
  ></base-todo-modal>

  <v-container
    v-if="todos.length === 0"
    fluid
    class="emptyDiv"
    :style="{ height: listHeight }"
  >
    <span>{{ emptyText }}한 항목이 없습니다.</span>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted, inject, watch } from "vue";
import { useRoute } from "vue-router";
import { todoListSettings } from "@/config/pageSettings";
import BaseTodoModal from "./BaseTodoModal.vue";
import api from "@/plugins/axios";

const todos = ref([]);
const errorMsg = ref("");

const todoCount = inject("todoCount");

watch(
  todos,
  (updatedTodos) => {
    todoCount.value = updatedTodos.length;
  },
  { deep: true, immediate: true }
);

onMounted(async () => {
  const response = await api.get("/todos");

  if (response.success) {
    todos.value = response.data;
  } else {
    errorMsg.value = response.message;
  }
});

const emit = defineEmits(["todoExist", "close"]);
emit("todoExist", todos.value.length > 0);

const route = useRoute();
// 페이지 별 투두 리스트 height
const listHeight = computed(() => todoListSettings[route.path]?.todoListHeight);

const emptyText = computed(
  () => todoListSettings[route.path]?.emptyText || "등록"
);

// 할 일 수정 및 삭제 모달
const todoModal = ref(false);

// 레이블 더블 클릭
const handleDoubleClick = (todo) => {
  todoModal.value = true;
};

// 모달 닫기
const closeTodoModal = () => {
  todoModal.value = false;
};

const resetModalData = () => {
  selectedDate.value = new Date();
  selectedTime.value = null;
  emit("close");
};
</script>

<style scoped>
.emptyDiv {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
</style>

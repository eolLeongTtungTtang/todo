<template>
  <v-container
    v-if="todos && todos.length > 0"
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
          v-model="checkedTodos"
          :value="todo.todoId"
        >
          <template #label>
            <v-label
              @dblclick="
                route.path !== '/completed' ? handleDoubleClick(todo) : ''
              "
            >
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
    v-if="!todos || todos.length === 0"
    fluid
    class="emptyDiv"
    :style="{ height: listHeight }"
  >
    <span>{{ emptyText }}한 항목이 없습니다.</span>
  </v-container>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useRoute } from "vue-router";
import { todoListSettings } from "@/config/pageSettings";
import BaseTodoModal from "./BaseTodoModal.vue";

// Props 정의
const props = defineProps({
  todos: Array,
  modelValue: Array,
  selectAll: Boolean,
});

// Emit 정의
const emit = defineEmits(["close", "update:modelValue"]);

// 상태 변수
const route = useRoute();
const todos = ref([]); // todo Array
const checkedTodos = ref([]); // 체크된 todo 배열
const todoModal = ref(false); // 할 일 수정 및 삭제 모달

// 페이지 별 todo 리스트의 height
const listHeight = computed(() => todoListSettings[route.path]?.todoListHeight);

// empty 텍스트 설정
const emptyText = computed(
  () => todoListSettings[route.path]?.emptyText || "등록"
);

// 초기화: checkedTodos가 배열인지 확인
onMounted(() => {
  if (!Array.isArray(checkedTodos.value)) {
    checkedTodos.value = [];
  }
});

// props.modelValue가 변경될 때 checkedTodos 업데이트
watch(
  () => props.modelValue,
  (newVal) => {
    if (Array.isArray(newVal)) {
      checkedTodos.value = newVal;
    }
  }
);

// props.todos가 변경될 때 todos 업데이트
watch(
  () => props.todos,
  (newTodos) => {
    todos.value = newTodos;
  },
  { immediate: true }
);

// selectAll이 변경될 때 전체 선택 상태 업데이트
watch(
  () => props.selectAll,
  (selectedAll) => {
    if (selectedAll) {
      checkedTodos.value = [...new Set(todos.value.map((todo) => todo.todoId))];
    } else {
      checkedTodos.value = [];
    }
  },
  { immediate: true }
);

// checkedTodos 변경 시 부모 컴포넌트로 값 전달
watch(checkedTodos, (newCheckedTodos) => {
  emit("update:modelValue", newCheckedTodos);
});

// 레이블 더블 클릭 시 모달 열기
const handleDoubleClick = (todo) => {
  todoModal.value = true;
};

// 모달 닫기
const closeTodoModal = () => {
  todoModal.value = false;
};

// 모달 데이터 초기화 및 emit
const resetModalData = () => {
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

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
          :class="getPriorityClass(todo.priority)"
          @click="route.path === '/completed' ? '' : completeTodo(todo.todoId)"
        >
          <template #label>
            <v-label
              @dblclick="
                route.path !== '/completed' ? handleDoubleClick(todo) : ''
              "
            >
              {{ todo.title }}
              <span
                class="spanTime"
                v-if="
                  route.path === '/all-todos' ||
                  route.path === '/completed' ||
                  route.path === '/calender'
                "
                @click.stop
              >
                {{ todo.dueTime ? todo.dueTime.slice(0, -3) : "하루종일" }}
              </span>
            </v-label>
          </template>
        </v-checkbox>
      </v-col>
    </v-row>
  </v-container>

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
import { ref, computed, watch, onMounted, inject } from "vue";
import { useRoute } from "vue-router";
import { todoListSettings } from "@/config/pageSettings";
import api from "@/plugins/axios";

const setMessage = inject("setMessage");
const todoModal = inject("todoModal");

// Props 정의
const props = defineProps({
  todos: Array,
  modelValue: Array,
  selectAll: Boolean,
});

// Emit 정의
const emit = defineEmits(["update:modelValue"]);

// 상태 변수
const route = useRoute();
const todos = ref([]); // todo Array
const checkedTodos = ref([]); // 체크된 todo 배열

const getPriorityClass = (priority) => {
  switch (priority) {
    case 1:
      return "checkbox-red"; // 빨간색 우선순위
    case 2:
      return "checkbox-blue"; // 파란색 우선순위
    case 3:
      return "checkbox-yellow"; // 노란색 우선순위
    default:
      return "";
  }
};

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

// 투두 완료처리
const completeTodo = async (id) => {
  try {
    const response = await api.put(`/todos/${id}/complete`);

    todos.value = todos.value.filter((todo) => todo.todoId !== id);

    const status = response.success ? "success" : "error";
    setMessage(response.message, status);
  } catch (e) {
    console.log(e);
  }
};

// 레이블 더블 클릭 시 모달 열기
const handleDoubleClick = (todo) => {
  todoModal(true, "edit", todo);
};
</script>

<style scoped>
/* 체크박스 아이콘의 색상 설정 */
::v-deep .checkbox-red .v-selection-control__input > .v-icon {
  background-color: red !important;
  border-radius: 20%;
}

::v-deep .checkbox-blue .v-selection-control__input > .v-icon {
  background-color: blue !important;
  border-radius: 20%;
}

::v-deep .checkbox-yellow .v-selection-control__input > .v-icon {
  background-color: yellow !important;
  border-radius: 20%;
}

/* 기본 상태에서 색상 */
::v-deep .v-selection-control__input > .v-icon {
  color: grey !important; /* 기본 색상 */
  opacity: var(--v-medium-emphasis-opacity) !important;
}

::v-deep .v-selection-control .v-label {
  white-space: normal;
  word-break: break-word;
  height: 100%;
  opacity: 1;
  width: 100% !important;
  justify-content: space-between !important;
}

.spanTime {
  width: 10vh;
  text-align: right;
  font-size: 14px;
  color: grey;
}

.emptyDiv {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
</style>

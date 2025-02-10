<template>
  <template v-if="todos && todos.length > 0">
    <v-btn class="selectAllBtn" variant="plain" @click="clickSelectAll">{{
      selectText
    }}</v-btn>
  </template>

  <base-todolist
    :todos="todos"
    :selectAll="selectAll"
    v-model="checkedTodos"
  ></base-todolist>

  <template v-if="todos && todos.length > 0">
    <div class="buttonDiv">
      <base-button
        variant="plain"
        action="delete"
        @click="(confirmDeleteModal = true), (deleteFlag = true)"
        @click:outside="closeDeleteModal"
        :disabled="checkedTodos.length === 0"
      ></base-button>
      <base-button
        variant="plain"
        action="restore"
        @click="(confirmDeleteModal = true), (restoreFlag = true)"
        @click:outside="closeDeleteModal"
        :disabled="checkedTodos.length === 0"
      ></base-button>
    </div>

    <v-dialog v-model="confirmDeleteModal" @click:outside="closeDeleteModal">
      <v-container class="confirmDeleteContainer">
        <v-row
          justify="center"
          align-items="center"
          style="background-color: white; border-radius: 4px"
        >
          <div class="text-center">
            <span class="spanText" v-if="deleteFlag">{{ deleteText }}</span>
            <span class="spanText" v-else>{{ restoreText }}</span>
          </div>

          <div class="confirmBtn">
            <base-button
              action="confirm"
              variant="plain"
              @click="actTodo"
            ></base-button>
            <base-button
              action="cancel"
              variant="plain"
              @click="closeDeleteModal"
            ></base-button>
          </div>
        </v-row>
      </v-container>
    </v-dialog>
  </template>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseButton from "@/components/ui/BaseButton.vue";
import { ref, toRaw, onMounted, inject, computed, watch } from "vue";
import api from "@/plugins/axios";

const setMessage = inject("setMessage");

const props = defineProps({
  setMessage: Function,
  reloadDataFlag: Boolean,
});

const todos = ref([]);
const checkedTodos = ref([]);
const confirmDeleteModal = ref(false);
const deleteFlag = ref(false);
const restoreFlag = ref(false);
const selectAll = ref(false);
const selectText = ref("전체선택");

const deleteText = `선택항목을 삭제합니다.\n삭제된 항목은 복구할 수 없습니다.`;
const restoreText = "선택항목을 복구합니다.";

const emit = defineEmits();

const reloadFlag = computed(() => props.reloadDataFlag);

watch(reloadFlag, (newFlag) => {
  if (newFlag) {
    fetchData();
    emit("update:reloadDataFlag", false);
  }
});

onMounted(() => {
  fetchTodos();
});

// 할일 목록을 불러오는 함수
const fetchTodos = async () => {
  try {
    const response = await api.get("/todos/completed");
    if (response.success) {
      todos.value = response.data;
    } else {
      setMessage(response.message, "error");
    }
  } catch (error) {
    console.error("Error fetching todos:", error);
  }
};

// 전체 선택 토글
const clickSelectAll = () => {
  selectAll.value = !selectAll.value;
  selectText.value = selectAll.value ? "전체해제" : "전체선택";
};

// 삭제/복구 모달 닫기
const closeDeleteModal = () => {
  deleteFlag.value = false;
  restoreFlag.value = false;
  confirmDeleteModal.value = false;
};

// 할일 삭제 및 복구 처리
const actTodo = async () => {
  const param = {
    ids: toRaw(checkedTodos.value),
  };

  let response;
  if (deleteFlag.value) {
    // 할일 삭제
    response = await api.delete("/todos/many", { data: param });

    deleteFlag.value = false;
  } else if (restoreFlag.value) {
    // 할일 복구
    response = await api.put("/todos/not_completed", param);

    restoreFlag.value = false;
  }

  const status = response.success ? "success" : "error";
  setMessage(response.message, status);

  if (response.success) {
    // 삭제/복구 후 목록을 다시 불러오기
    await fetchTodos();
  }

  confirmDeleteModal.value = false;
};
</script>

<style scoped>
.spanText {
  white-space: pre-line;
}

.selectAllBtn {
  align-self: flex-start;
  font-weight: normal;
  text-decoration: underline;
  font-size: 15px;
  margin-bottom: 10px;
}

.buttonDiv {
  justify-content: center;
  position: absolute;
  bottom: 5vh;
  width: 100%;
  display: flex;
  align-items: center;
  padding: 0 20px;
  gap: 10px;
}

.confirmDeleteContainer {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  width: 45vh;
  height: 30vh;
  border: 2px solid black;
  border-radius: 20px !important;
  background-color: white;
}

.confirmBtn {
  margin-top: 4vh;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 10px;
  margin-bottom: 3vh;
}
</style>

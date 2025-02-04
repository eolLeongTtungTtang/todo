<template>
  <template v-if="todoExist">
    <v-btn class="selectAllBtn" variant="plain" @click="clickSelectAll">{{
      selectText
    }}</v-btn>
  </template>

  <base-todolist @todo-exist="updateTodoExist"></base-todolist>

  <template v-if="todoExist">
    <div class="buttonDiv">
      <base-button
        variant="plain"
        action="delete"
        @click="(confirmDeleteModal = true), (deleteFlag = true)"
        @click:outside="closeDeleteModal"
      ></base-button>
      <base-button
        variant="plain"
        action="restore"
        @click="(confirmDeleteModal = true), (restoreFlag = true)"
        @click:outside="closeDeleteModal"
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
            <span class="spanText" v-if="restoreFlag">{{ restoreText }}</span>
          </div>
          <div class="confirmBtn">
            <base-button action="confirm" variant="plain" @click="actTodo"
              >확인</base-button
            >
            <base-button
              action="cancel"
              variant="plain"
              @click="closeDeleteModal"
              >취소</base-button
            >
          </div>
        </v-row>
      </v-container>
    </v-dialog>
  </template>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseButton from "@/components/ui/BaseButton.vue";
import { ref, watch, computed } from "vue";

let todoExist = ref(false);

const updateTodoExist = (exist) => {
  todoExist.value = exist;
};

let selectAll = ref(false);
let selectText = ref("전체선택");
// base-todolist에 props로 전체 선택 전달 해야함
const clickSelectAll = () => {
  selectAll.value = !selectAll.value;
  selectText.value = selectAll.value ? "전체해제" : "전체선택";
};

const deleteFlag = ref(false);
const restoreFlag = ref(false);

watch(deleteFlag, () => console.log("deleteFlag", deleteFlag.value));
watch(restoreFlag, () => console.log("restoreFlag", restoreFlag.value));

const closeDeleteModal = () => {
  deleteFlag.value = false;
  restoreFlag.value = false;
  confirmDeleteModal.value = false;
};

const deleteText = `선택항목을 삭제합니다.\n삭제된 항목은 복구할 수 없습니다.`;
const restoreText = "선택항목을 복구합니다.";

// TODO 삭제
let confirmDeleteModal = ref(false);

const actTodo = () => {
  if (deleteFlag) {
    // 할일 삭제 api 호출
  }

  if (restoreFlag) {
    // 할일 복구 api 호출
  }

  confirmDeleteModal = false;
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

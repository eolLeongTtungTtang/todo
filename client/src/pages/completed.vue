<template>
  <template v-if="todoExist">
    <v-btn class="selectAllBtn" variant="plain" @click="clickSelectAll">{{
      selectText
    }}</v-btn>
  </template>

  <base-todolist @todo-exist="updateTodoExist"></base-todolist>

  <template v-if="todoExist">
    <div class="buttonDiv">
      <base-button variant="plain" action="delete"></base-button>
      <base-button variant="plain" action="restore"></base-button>
    </div>
  </template>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseButton from "@/components/ui/BaseButton.vue";
import { ref } from "vue";

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
</script>

<style scoped>
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
  gap: 20px;
}
</style>

<template>
  <v-expansion-panels flat v-model="panel">
    <v-expansion-panel v-for="(title, index) in tagArr" :key="index">
      <v-expansion-panel-title>
        <span class="titleText"># {{ title.tag }}</span>
        <span class="greyText">&nbsp;&nbsp;+{{ title.maxTodoId }}</span>
        <template v-slot:actions="{ expanded }">
          <span class="toggleText">{{ expanded ? "닫기" : "펼치기" }}</span>
        </template>
      </v-expansion-panel-title>

      <v-expansion-panel-text class="panelText">
        <base-todolist />
      </v-expansion-panel-text>

      <base-divider
        v-if="tagArr.length - 1 !== index"
        thickness="1"
        divider-margin="0"
      />
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseDivider from "@/components/ui/BaseDivider.vue";
import { ref } from "vue";
import todoByTagTest from "../data/todoByTagTest.json";

let panel = ref(null);

// 태그
const tagArr = Object.entries(todoByTagTest).map(([tag, todoList]) => {
  // reduce를 사용하여 최대 todo_id 값을 찾기
  const maxTodoId = todoList.reduce(
    (max, todo) => Math.max(max, todo.todo_id),
    -Infinity
  );

  return { tag, maxTodoId };
});
</script>

<style scoped>
::v-deep .v-expansion-panel-text__wrapper {
  padding: 0 !important;
}

.titleText {
  font-weight: bold;
  font-size: 16px;
}

.panelText {
  font-weight: normal;
  padding: 0 !important;
}

.toggleText {
  font-weight: normal;
  text-decoration: underline;
  font-size: 15px;
}
</style>

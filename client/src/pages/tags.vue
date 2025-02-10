<template>
  <v-expansion-panels flat v-model="panel">
    <v-expansion-panel v-for="(todo, tag, index) in todos" :key="index">
      <v-expansion-panel-title>
        <span class="titleText"># {{ tag }}</span>
        <span class="greyText">&nbsp;&nbsp;+{{ todo.length }}</span>
        <template v-slot:actions="{ expanded }">
          <span class="toggleText">{{ expanded ? "닫기" : "펼치기" }}</span>
        </template>
      </v-expansion-panel-title>

      <v-expansion-panel-text class="panelText">
        <base-todolist :todos="todo" />
      </v-expansion-panel-text>

      <base-divider
        v-if="index !== todosLength - 1"
        thickness="1"
        divider-margin="0"
      />
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseDivider from "@/components/ui/BaseDivider.vue";
import { ref, onMounted, watch, computed } from "vue";
import api from "@/plugins/axios";

const props = defineProps({
  reloadDataFlag: Boolean,
});

const emit = defineEmits();

const reloadFlag = computed(() => props.reloadDataFlag);

watch(reloadFlag, (newFlag) => {
  if (newFlag) {
    fetchData();
    emit("update:reloadDataFlag", false);
  }
});

let panel = ref(null);
const todos = ref({});
const todosLength = ref(0);

onMounted(() => {
  fetchData();
});

const fetchData = async () => {
  const response = await api.get("/todos/tag");

  if (response.success) {
    todos.value = response.data;
    todosLength.value = Object.entries(todos.value).length;
  } else {
    errorMsg.value = response.message;
  }
};
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

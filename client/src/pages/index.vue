<template>
  <base-todo-summary v-if="route.path === '/'" :todoCount="count" />

  <!-- todo list -->
  <base-todolist :todos="todos" />

  <base-divider />

  <!-- move to button menu -->
  <calender-button />
  <comp-and-tag-button />
</template>

<script setup>
import BaseTodoSummary from "@/components/ui/BaseTodoSummary.vue";
import BaseDivider from "@/components/ui/BaseDivider.vue";
import CalenderButton from "@/components/CalenderButton.vue";
import CompAndTagButton from "@/components/CompAndTagButton.vue";
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import api from "@/plugins/axios";

const route = useRoute();
const todos = ref([]);
const count = ref(0); // todo 개수

onMounted(async () => {
  if (route.path === "/") {
    const response = await api.get("/todos");

    if (response.success) {
      todos.value = response.data;
      count.value = todos.value.length;
    } else {
      errorMsg.value = response.message;
    }
  }
});
</script>

<style></style>

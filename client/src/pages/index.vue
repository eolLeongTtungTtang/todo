<template>
  <div class="container">
    <todo-summary />

    <base-divider />

    <!-- todo list -->
    <base-todolist />

    <base-divider />

    <!-- move to button menu -->
    <calender-button />
    <comp-and-tag-button />
  </div>
</template>

<script setup>
import BaseDivider from "@/components/ui/BaseDivider.vue";
import CalenderButton from "@/components/CalenderButton.vue";
import CompAndTagButton from "@/components/CompAndTagButton.vue";
import TodoSummary from "@/components/TodoSummary.vue";
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

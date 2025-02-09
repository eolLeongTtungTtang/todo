<template>
  <v-locale-provider locale="ko">
    <v-date-picker
      class="datePicker"
      v-model="selectedDate"
      hide-header
    ></v-date-picker>
  </v-locale-provider>

  <base-divider thickness="1" dividerMargin="0" />

  <v-container class="todoContainer">
    <base-todolist :todos="todos" />
  </v-container>
</template>

<script setup>
import BaseDivider from "@/components/ui/BaseDivider.vue";
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import { onMounted, ref, inject, computed, watch } from "vue";
import api from "@/plugins/axios";

const setMessage = inject("setMessage");

const selectedDate = ref(new Date());
const todos = ref([]);

const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작해서 1을 더해줌
  const day = String(date.getDate()).padStart(2, "0"); // 일이 1자리 숫자일 경우 앞에 0을 추가

  return `${year}-${month}-${day}`;
};

// formattedSelectedDate는 'YYYY-MM-DD' 형식
const formattedSelectedDate = computed(() => formatDate(selectedDate.value));

watch(selectedDate, () => {
  fetchTodo();
});

onMounted(() => {
  fetchTodo();
});

const fetchTodo = async () => {
  try {
    const param = {
      date: formattedSelectedDate.value,
    };
    const response = await api.get("/todos", { params: param });
    const status = response.success ? "success" : "error";

    if (response.success) {
      todos.value = response.data;
    }
    setMessage(response.message, status);
  } catch (e) {
    console.log(e);
  }
};
</script>

<style scoped>
.datePicker {
  padding-top: 0;
  padding-bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.datePickerContainer {
  padding-top: 0;
  padding-bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.todoContainer {
  padding-top: 5px;
}
</style>

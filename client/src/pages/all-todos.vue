<template>
  <v-expansion-panels flat v-model="panel">
    <v-expansion-panel title="지난 할 일">
      <v-expansion-panel-text>
        <base-todolist :todos="todosLast" />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>

  <base-divider v-if="dividerFlag" thickness="1" divider-margin="0" />

  <v-expansion-panels flat>
    <v-expansion-panel title="오늘 할 일">
      <v-expansion-panel-text>
        <base-todolist :todos="todosToday" />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseDivider from "@/components/ui/BaseDivider.vue";
import { ref, computed, onMounted, inject, watch } from "vue";
import api from "@/plugins/axios";

let panel = ref(null);
const todosToday = ref([]);
const todosLast = ref([]);

const setMessage = inject("setMessage");

const props = defineProps(["reloadDataFlag"]);

const emit = defineEmits();

const reloadFlag = computed(() => props.reloadDataFlag);

watch(reloadFlag, (newFlag) => {
  if (newFlag) {
    fetchData();
    emit("update:reloadDataFlag", false);
  }
});

let dividerFlag = computed(() => {
  return typeof panel.value !== "number";
});

onMounted(() => {
  fetchData();
});

const fetchData = async () => {
  try {
    const response = await api.get("/todos/last");

    if (response.success) {
      todosToday.value = response.data.today;
      todosLast.value = response.data.last;
    } else {
      setMessage(response.message, "error");
    }
  } catch (e) {
    console.log(e);
  }
};
</script>

<style scoped>
.v-expansion-panel {
  font-weight: bold !important;
}

.v-expansion-panel-text {
  font-weight: normal;
}

::v-deep .v-expansion-panel-text__wrapper {
  padding: 0 !important;
}
</style>

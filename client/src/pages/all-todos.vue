<template>
  <v-expansion-panels flat v-model="panel">
    <v-expansion-panel title="지난 할 일">
      <v-expansion-panel-text>
        <base-todolist />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>

  <base-divider v-if="dividerFlag" thickness="1" divider-margin="0" />

  <v-expansion-panels flat>
    <v-expansion-panel title="오늘 할 일">
      <v-expansion-panel-text>
        <base-todolist />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup>
import BaseTodolist from "@/components/ui/BaseTodolist.vue";
import BaseDivider from "@/components/ui/BaseDivider.vue";
import { ref, computed } from "vue";

let panel = ref(null);
let dividerFlag = computed(() => {
  return typeof panel.value !== "number";
});
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

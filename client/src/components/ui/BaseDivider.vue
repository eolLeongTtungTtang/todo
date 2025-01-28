<template>
  <v-divider
    :style="{ borderWidth: props.thickness, margin: props.dividerMargin }"
    class="divider"
    width="350px"
  ></v-divider>
</template>

<script setup>
const props = defineProps({
  thickness: {
    type: String,
    default: "1.1px",
  },
  dividerMargin: {
    type: String,
    default: "12px 0 15px",
  },
});
</script>

<style scoped>
.divider {
  border-color: black;
  opacity: 1 !important;
}
</style>

<template>
  <v-btn class="btnText">{{ btnText }}</v-btn>
</template>

<script setup>
const { action } = defineProps({
  action: String,
});

const btnText = action === "delete" ? "삭제" : "복구";
</script>

<style scoped>
.btnText {
  font-size: 17px;
  font-weight: bold;
  /* 삭제 버튼 */
  /* outline: auto #029356;
  color: #029356; */

  /* 수정 버튼 */
  background-color: #029356;
  color: white;
}
</style>

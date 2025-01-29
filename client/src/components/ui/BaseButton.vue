<template>
  <v-btn class="btnText" :style="btnStyle">{{ btnText }}</v-btn>
</template>

<script setup>
const { action } = defineProps({
  action: String,
});

const btnTextObj = {
  delete: {
    text: "삭제",
    btnStyle: {
      outline: "auto",
      color: "#029356",
    },
  },
  restore: {
    text: "복구",
    btnStyle: {
      backgroundColor: "#029356",
      color: "white",
    },
  },
  confirm: {
    text: "확인",
    btnStyle: {
      backgroundColor: "#029356",
      color: "white",
    },
  },
  cancel: {
    text: "취소",
    btnStyle: {
      outline: "auto",
      color: "#029356",
    },
  },
  edit: {
    text: "수정",
    btnStyle: {
      backgroundColor: "#029356",
      color: "white",
    },
  },
  save: {
    text: "저장",
    btnStyle: {
      backgroundColor: "#029356",
      color: "white",
    },
  },
};
const { text: btnText, btnStyle } = btnTextObj[action];
</script>

<style scoped>
.btnText {
  font-size: 17px;
  font-weight: bold;
}
</style>

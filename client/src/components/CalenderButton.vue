<template>
  <RouterLink to="/calender" class="btn-wrapper">
    <v-btn
      class="btnText"
      color="#029356"
      height="60px"
      rounded="lg"
      elevation="0"
      append-icon="mdi-calendar"
      text="캘린더로 보기"
    ></v-btn>
  </RouterLink>
</template>

<style scoped>
.btn-wrapper {
  display: block;
  width: 100%;
}

.btnText {
  width: 100%;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}
</style>

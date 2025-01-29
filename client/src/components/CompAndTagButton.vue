<template>
  <div class="rowBtns">
    <div class="btnDiv">
      <RouterLink to="/completed" class="btnWrapper">
        <v-btn
          color="#b9e192"
          height="60px"
          rounded="lg"
          elevation="0"
          append-icon="mdi-check-circle"
          text="완료 항목"
          class="btnText"
        ></v-btn>
      </RouterLink>
    </div>
    <div class="btnDiv">
      <RouterLink to="/tags" class="btnWrapper">
        <v-btn
          color="#b9e192"
          height="60px"
          rounded="lg"
          elevation="0"
          append-icon="mdi-pound"
          text="태그별 조회"
          class="btnText"
        ></v-btn>
      </RouterLink>
    </div>
  </div>
</template>

<style scoped>
.rowBtns {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 14px;
  width: 100%;
}

.btnDiv {
  display: flex;
  justify-content: center;
  width: 100%;
}

.btnWrapper {
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

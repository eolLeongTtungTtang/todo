<template>
  <div class="rowBtns">
    <div class="btnDiv">
      <RouterLink to="/completed"
        ><v-btn
          color="#b9e192"
          width="167px"
          height="60px"
          rounded="lg"
          elevation="0"
          append-icon="mdi-check-circle"
          text="완료 항목"
          class="btnText"
        ></v-btn
      ></RouterLink>
    </div>
    <div class="btnDiv">
      <RouterLink to="/tags"
        ><v-btn
          color="#b9e192"
          width="167px"
          height="60px"
          rounded="lg"
          elevation="0"
          append-icon="mdi-pound"
          text="태그별 조회"
          class="btnText"
        ></v-btn
      ></RouterLink>
    </div>
  </div>
</template>

<style>
.rowBtns {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 14px;
}

.btnDiv {
  display: flex;
  justify-content: center;
}

.btnText {
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}
</style>

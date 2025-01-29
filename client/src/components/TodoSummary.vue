<template>
  <div class="countAndAllContainer">
    <div class="todoCountDiv">
      <span>할 일 <span class="greyText">+13</span></span>
    </div>
    <RouterLink to="/all-todos"
      ><v-btn
        class="moreBtn"
        append-icon="mdi-chevron-right"
        variant="plain"
        text="더보기"
      ></v-btn
    ></RouterLink>
  </div>
</template>

<style>
.countAndAllContainer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 15px;
  margin-top: 25px;
}

.todoCountDiv {
  margin-left: 20px;
  font-size: 15px;
  font-weight: bold;
}

.greyText {
  color: rgb(168, 168, 168);
}

.moreBtn {
  color: black;
  opacity: 1;
}
</style>

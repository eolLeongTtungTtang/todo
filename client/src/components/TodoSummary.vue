<template>
  <div class="countAndAllContainer">
    <div class="todoCountDiv">
      <span
        >할 일 <span class="greyText">+{{ todoCount }}</span></span
      >
    </div>
    <RouterLink to="/all-todos"
      ><v-btn class="moreBtn" append-icon="mdi-chevron-right" variant="plain"
        ><span>더보기</span></v-btn
      ></RouterLink
    >
  </div>
</template>

<script setup>
import { inject } from "vue";

const todoCount = inject("todoCount");
</script>

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
  font-size: 17px;
  font-weight: bold;
}

.moreBtn {
  color: black;
  opacity: 1;
  font-size: 12px;
  color: rgb(168, 168, 168);
}
</style>

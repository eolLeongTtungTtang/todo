<template>
  <v-btn
    @click="dialog = true"
    :style="{
      visibility: addbtnShow,
    }"
    class="addBtn"
    :icon="headerBtnIcon"
    elevation="0"
    size="x-large"
  ></v-btn>

  <v-dialog v-model="dialog" width="90%" height="85%">
    <v-card
      max-width="100%"
      max-height="100%"
      :title="modalTitle"
      class="outlined-dialog"
    >
      <v-container class="inputContainer">
        <v-text-field
          hide-details
          class="inputTodo"
          density="compact"
          placeholder="할 일을 입력해 주세요."
          variant="outlined"
        ></v-text-field>

        <base-divider thickness="1" />

        <!-- input datePicker -->

        <span class="spanTitle">2025-01-30&nbsp</span>
        <v-icon size="sm">mdi-calendar-today-outline</v-icon>
        <span class="spanTitle">하루종일&nbsp</span
        ><v-icon size="sm">mdi-all-inclusive</v-icon>
        <p class="spanTitle">메모</p>
        <v-textarea
          class="inputMemo"
          hide-details
          density="compact"
          placeholder="메모를 입력해 주세요."
          variant="outlined"
        ></v-textarea>

        <base-divider thickness="1" />

        <span class="spanTitle">중요도</span>
        <v-radio-group v-model="selected" inline hide-details>
          <v-radio
            v-for="(radio, index) in radioOptions"
            :key="index"
            :value="radio.value"
            :color="radio.color"
            :class="radio.class"
          ></v-radio>
        </v-radio-group>

        <span class="spanTitle">태그</span>
      </v-container>

      <v-container class="btnContent">
        <v-btn variant="plain" class="tagBtn" v-for="tag in tagArr"
          ># {{ tag }}</v-btn
        >
      </v-container>

      <template v-slot:actions>
        <div class="confirmBtn">
          <base-button
            action="confirm"
            variant="plain"
            @click="dialog = false"
          ></base-button>
          <base-button
            action="cancel"
            variant="plain"
            @click="dialog = false"
          ></base-button>
        </div>
      </template>
    </v-card>
  </v-dialog>
</template>

<script setup>
import BaseButton from "./ui/BaseButton.vue";
import BaseDivider from "./ui/BaseDivider.vue";
import { ref, defineProps } from "vue";

const props = defineProps({
  addbtnShow: {
    type: String,
  },
  headerBtnIcon: {
    type: String,
  },
});

const selected = ref("");
const radioOptions = [
  { value: 1, color: "red", class: "radio-red" },
  { value: 2, color: "blue", class: "radio-blue" },
  { value: 3, color: "yellow", class: "radio-yellow" },
];

const dialog = ref(false);
const modalTitle = ref("할 일 추가");

const tagArr = ["자택", "학교", "직장", "기타"];
</script>

<style scoped>
.addBtn.v-btn--density-default,
.addBtn.v-btn--icon.v-btn--density-default {
  width: 22px;
  height: 21px;
  margin: 8px;
  padding: 0 !important;
  min-width: 0 !important;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* v-dialog의 아웃라인 스타일 */
.outlined-dialog {
  border: 2px solid black; /* 원하는 색상과 두께로 설정 */
  border-radius: 20px !important; /* 테두리 둥글기 */
}

/* 공통 스타일 */
.inputTodo :deep(.v-input__control),
.inputMemo :deep(.v-input__control) {
  border-radius: 0 !important;
}

.inputTodo :deep(.v-field),
.inputMemo :deep(.v-field) {
  border-radius: 0 !important;
}

/* .inputMemo에만 적용되는 스타일 */
.inputMemo :deep(.v-field__input) {
  font-size: 14px !important;
}

/* .inputTodo에만 적용되는 스타일 */
.inputTodo :deep(.v-field__input) {
  font-size: 14px !important; /* 폰트 크기 적용 */
  padding: 5px 10px !important; /* 내부 여백 조정 */
}

.inputTodo {
  height: 32px !important; /* 높이 조정 */
  border-radius: 0 !important; /* 테두리 둥근 정도 없애기 */
}

.inputContainer {
  padding: 0 10px 0 10px;
}

.spanTitle {
  font-size: 14px;
  margin-left: 15px;
}

.btnContent {
  margin: 0;
  padding: 0 3px 0 3px;
  display: flex;
  justify-content: center;
}

.tagBtn {
  padding: 0;
  margin: 5px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  border-radius: 0 !important;
  outline: 1.5px solid rgb(87, 87, 87) !important;
  outline-offset: -1px;
  background-color: transparent;
}

.confirmBtn {
  margin-top: 4vh;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 10px;
  margin-bottom: 4vh;
}

.v-radio-group .v-input__details {
  padding-inline: 0px;
  grid-area: control;
}

.radio-red :deep(.v-selection-control__input) {
  background-color: red !important;
  width: 19px;
  height: 19px;
}

.radio-blue :deep(.v-selection-control__input) {
  background-color: blue !important;
  width: 19px;
  height: 19px;
}

.radio-yellow :deep(.v-selection-control__input) {
  background-color: yellow !important;
  width: 19px;
  height: 19px;
}
</style>

<template>
  <v-dialog width="90%" height="85%">
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

        <div class="dateAndTimeDiv">
          <button class="dateAndTimeSelector" @click="openCalender">
            <span>{{ formattedDate }}&nbsp</span>
            <v-icon size="sm">mdi-calendar-today-outline</v-icon>
          </button>
          <!-- 캘린더 모달 -->
          <v-dialog v-model="calenderModal">
            <v-locale-provider locale="ko">
              <v-date-picker
                class="datePicker"
                v-model="selectedDate"
                hide-header
              ></v-date-picker>
            </v-locale-provider>
          </v-dialog>

          <button class="dateAndTimeSelector" @click="openTimePicker">
            <template v-if="selectedTime">
              <span>{{ selectedTime }}&nbsp</span
              ><v-icon size="sm">mdi-archive-clock-outline</v-icon>
            </template>
            <template v-else
              ><span>하루종일&nbsp</span
              ><v-icon size="sm">mdi-all-inclusive</v-icon></template
            >
          </button>
          <!-- 시간 선택 모달 -->
          <v-dialog v-model="timePickerModal">
            <v-container>
              <v-row
                justify="center"
                align-items="center"
                style="background-color: white; border-radius: 4px"
              >
                <v-time-picker
                  title="시간을 선택해주세요."
                  color="green-lighten-1"
                  format="24hr"
                  v-model="selectedTime"
                />
                <div class="confirmBtn">
                  <base-button
                    action="confirm"
                    variant="plain"
                    @click="timePickerModal = false"
                  ></base-button>
                  <base-button
                    action="delete"
                    variant="plain"
                    @click="(timePickerModal = false), (selectedTime = null)"
                  ></base-button>
                </div>
              </v-row>
            </v-container>
          </v-dialog>
        </div>

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
        <v-radio-group v-model="selectedPriority" inline hide-details>
          <v-radio
            v-for="(radio, index) in radioOptions"
            :key="index"
            :value="radio.priority"
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
          <template v-if="mode === 'add'">
            <base-button
              action="confirm"
              variant="plain"
              @click="addTodo"
            ></base-button>
            <base-button
              action="cancel"
              variant="plain"
              @click="cancelTodo"
            ></base-button>
          </template>
          <template v-if="mode === 'edit'">
            <base-button
              action="edit"
              variant="plain"
              @click="editTodo"
            ></base-button>
            <base-button
              action="delete"
              variant="plain"
              @click="confirmDeleteModal = true"
            ></base-button>
          </template>
        </div>
      </template>

      <v-dialog
        v-model="confirmDeleteModal"
        @click:outside="confirmDeleteModal = false"
      >
        <v-container class="confirmDeleteContainer">
          <v-row
            justify="center"
            align-items="center"
            style="background-color: white; border-radius: 4px"
          >
            <span class="mb-1">할 일을 삭제합니다.</span>
            <div class="confirmBtn">
              <base-button action="confirm" variant="plain" @click="deleteTodo"
                >확인</base-button
              >
              <base-button
                action="cancel"
                variant="plain"
                @click="confirmDeleteModal = false"
                >취소</base-button
              >
            </div>
          </v-row>
        </v-container>
      </v-dialog>
    </v-card>
  </v-dialog>
</template>

<script setup>
import BaseButton from "./BaseButton.vue";
import BaseDivider from "./BaseDivider.vue";
import { VTimePicker } from "vuetify/labs/VTimePicker";
import { ref, watch, computed, defineProps } from "vue";

const props = defineProps({
  mode: String,
});

const mode = props.mode;

const modalType = {
  add: {
    title: "추가",
  },
  edit: {
    title: "수정",
  },
};

const modalTitle = ref("할 일 " + modalType[mode].title);

const selectedPriority = ref(null);
const radioOptions = [
  { priority: 1, color: "red", class: "radio-red" },
  { priority: 2, color: "blue", class: "radio-blue" },
  { priority: 3, color: "yellow", class: "radio-yellow" },
];

const tagArr = ["자택", "학교", "직장", "기타"];

// 캘린더 모달
const calenderModal = ref(false);
const openCalender = () => {
  calenderModal.value = !calenderModal.value;
};

// 날짜 선택
const selectedDate = ref(new Date());

// 날짜 포맷
const formattedDate = computed(() => {
  const date = new Date(selectedDate.value);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
});

// 날짜 변경 시 모달 닫기
watch(selectedDate, (newDate) => {
  console.log("날짜 변경: ", newDate);
  calenderModal.value = false;
});

// 시간 선택
const selectedTime = ref(null);

// 시간 선택 모달
const timePickerModal = ref(false);
const openTimePicker = () => {
  timePickerModal.value = !timePickerModal.value;
};

// 시간 선택 완료 시 모달 닫기
watch(selectedDate, (newTime) => {
  console.log("시간 변경: ", newTime);
  timePickerModal.value = false;
});

// TODO 추가
const addTodo = (data) => {
  // 할일 추가 api 호출
  resetModalData();
};

// TODO 추가 취소
const cancelTodo = () => {
  resetModalData();
};

// TODO 수정
const editTodo = () => {
  // 할일 update api 호출
  resetModalData();
};

// TODO 삭제
let confirmDeleteModal = ref(false);

const deleteTodo = () => {
  // 할일 삭제 api 호출
  confirmDeleteModal.value = false;
  resetModalData();
};

const emit = defineEmits(["close"]);

const resetModalData = () => {
  selectedDate.value = new Date();
  selectedTime.value = null;
  emit("close");
};
</script>

<style scoped>
.dateAndTimeDiv {
  display: flex;
  font-size: 14px;
}

.dateAndTimeSelector {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 10px;
  margin-bottom: 8px;
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
  margin-left: 10px;
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
  margin-bottom: 3vh;
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

.confirmDeleteContainer {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  width: 45vh;
  height: 30vh;
  border: 2px solid black;
  border-radius: 20px !important;
  background-color: white;
}
</style>

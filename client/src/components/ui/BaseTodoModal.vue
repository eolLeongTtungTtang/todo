<template>
  <v-dialog min-width="80%" min-height="85%" persistent>
    <v-card
      max-width="100%"
      max-height="100%"
      :title="modalTitle"
      class="outlined-dialog"
    >
      <v-btn icon @click="$emit('reload')" class="close-btn">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-container class="inputContainer">
        <!-- 폼 감싸기 -->
        <v-form ref="form" v-model="isValid">
          <!-- 할 일 제목 -->
          <v-text-field
            v-model="copyTodo.title"
            hide-details="auto"
            class="inputTodo"
            density="compact"
            placeholder="할 일을 입력해 주세요."
            variant="outlined"
            :rules="[() => !!copyTodo.title || '할 일을 입력해 주세요.']"
            required
          ></v-text-field>

          <base-divider thickness="1" />

          <!-- 날짜와 시간 선택 등... -->

          <span class="spanTitle">메모</span>
          <v-textarea
            v-model="copyTodo.memo"
            class="inputMemo"
            hide-details="auto"
            density="compact"
            placeholder="메모를 입력해 주세요."
            maxlength="50"
            hint="50자 이내로 입력해 주세요."
            variant="outlined"
          ></v-textarea>

          <base-divider thickness="1" />

          <span class="spanTitle">중요도</span>
          <v-radio-group v-model="copyTodo.priority" inline hide-details>
            <v-radio
              v-for="(radio, index) in radioOptions"
              :key="index"
              :value="radio.priority"
              :color="radio.color"
              :class="radio.class"
            ></v-radio>
          </v-radio-group>

          <span class="spanTitle">태그</span>
        </v-form>
        <!-- v-form 종료 -->
      </v-container>

      <v-container class="btnContent">
        <v-btn
          v-model="copyTodo.tag"
          :class="['tagBtn', { selected: copyTodo.tag === tag }]"
          variant="plain"
          @click="selectTag(tag)"
          v-for="tag in tagArr"
          :key="tag"
        >
          # {{ tag }}
        </v-btn>
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
    </v-card>
  </v-dialog>
</template>

<script setup>
import BaseButton from "./BaseButton.vue";
import BaseDivider from "./BaseDivider.vue";
import { VTimePicker } from "vuetify/labs/VTimePicker";
import { ref, watch, computed, reactive, inject, toRaw } from "vue";
import api from "@/plugins/axios";

const emit = defineEmits(["close", "reload"]);
const setMessage = inject("setMessage");

const props = defineProps({
  mode: String,
  todo: Object,
});

const copyTodo = reactive({
  ...props.todo,
});
const isValid = ref(false);
const form = ref(null);

// props.todo가 변경될 때마다 copyTodo 업데이트
watch(
  () => props.todo,
  (newTodo) => {
    Object.assign(copyTodo, newTodo);
  }
);

const rules = {
  title: [(v) => !!v || "할 일을 입력해 주세요."],
};

// TODO 삭제 확인 모달
let confirmDeleteModal = ref(false);

// 모드에 따른 제목 설정
const mode = computed(() => props.mode);

const modalType = {
  add: { title: "추가" },
  edit: { title: "수정" },
};

const modalTitle = computed(() => {
  return `할 일 ${modalType[mode.value]?.title}` || "";
});

watch(
  () => mode.value,
  (newMode) => {
    if (newMode === "add") {
      copyTodo.title = "";
      copyTodo.memo = "";
      copyTodo.dueDate = "";
      copyTodo.dueTime = "";
      copyTodo.priority = null;
      copyTodo.isCompleted = false;
      copyTodo.tag = null;
    }
  },
  { immediate: true } // 초기화 시점에 즉시 실행
);

// 중요도
const radioOptions = [
  { priority: 1, color: "red", class: "radio-red" },
  { priority: 2, color: "blue", class: "radio-blue" },
  { priority: 3, color: "yellow", class: "radio-yellow" },
];

// 태그
const tagArr = ["자택", "학교", "직장", "기타"];

const selectTag = (tag) => {
  copyTodo.tag = tag;
};

// 캘린더 모달
const calenderModal = ref(false);
const openCalender = () => {
  calenderModal.value = !calenderModal.value;
};

// 날짜 선택
const selectedDate = ref(copyTodo.dueDate || new Date());

// 날짜 포맷
const formattedDate = (pickedDate) => {
  const date = new Date(pickedDate);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

// 날짜 변경 시 모달 닫기
watch(selectedDate, (newDate) => {
  selectedDate.value = newDate;
  calenderModal.value = false;
});

// 시간 선택
const selectedTime = ref(copyTodo.dueTime || null);

// 시간 선택 모달
const timePickerModal = ref(false);
const openTimePicker = () => {
  timePickerModal.value = !timePickerModal.value;
};

// 시간 선택 완료 시 모달 닫기
watch(selectedTime, (newTime) => {
  selectedTime.value = newTime;
  timePickerModal.value = false;
});

// TODO 추가
const addTodo = async () => {
  // 폼이 유효한지 확인
  if (form.value) {
    const { valid } = await form.value.validate(); // validate()는 Promise를 반환함

    if (!valid) {
      return;
    }
  }

  // 유효성 검사가 완료되면 할일 추가 API 호출
  const editTodo = toRaw(copyTodo);
  const param = {
    title: editTodo.title,
    memo: editTodo.memo,
    dueDate: selectedDate.value,
    dueTime: selectedTime.value,
    priority: editTodo.priority,
    tag: editTodo.tag,
  };

  try {
    const response = await api.post("/todos", param);
    emit("reload", response);
  } catch (e) {
    console.log(e);
  }
};

// TODO 추가 취소
const cancelTodo = () => {
  emit("reload");
  emit("close");
};

// TODO 수정
const editTodo = async () => {
  // 할일 update api 호출
  const editTodo = toRaw(copyTodo);
  const param = {
    title: editTodo.title,
    memo: editTodo.memo,
    dueDate: editTodo.dueDate,
    dueTime: editTodo.dueTime,
    priority: editTodo.priority,
    tag: editTodo.tag,
  };
  try {
    const response = await api.put(`/todos/${editTodo.todoId}`, param);

    confirmDeleteModal.value = false;
    emit("reload", response);
  } catch (e) {
    console.log(e);
  }
};

const deleteTodo = async () => {
  // 할일 삭제 api 호출
  try {
    const response = await api.delete(`/todos/${props.todo.todoId}`);

    confirmDeleteModal.value = false;
    emit("reload", response);
  } catch (e) {
    console.log(e);
  }
};
</script>

<style scoped>
.selected {
  background-color: #3f9e7f !important;
  color: white !important;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: transparent;
  color: #000;
  box-shadow: none !important;
}

.close-btn .v-icon {
  font-size: 24px;
}

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
  min-height: 32px !important; /* 높이 조정 */
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

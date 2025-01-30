package com.eolLeongTtungTtang.todo.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Getter
@Setter
public class TodoDTO {

    private Integer todoId;
    private String title;
    private String memo;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private Integer priority;
    private String tag;
    private Boolean isCompleted;
    private LocalDate createdAt;
    private LocalDate updatedAt;


    // MyBatis는 Optional을 직접 지원하지 않으므로 String으로 받고 변환
    public Optional<String> getTag() {
        return Optional.ofNullable(tag);
    }


}

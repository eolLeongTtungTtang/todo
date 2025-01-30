package com.eolLeongTtungTtang.todo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {

    private Boolean success;
    private String message;
    private Object data;

}

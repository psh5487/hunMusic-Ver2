package com.music.controller.network;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiError {
    // 오류 메시지
    private final String message;

    // HTTP 오류 코드
    private final int status;

    ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }

    ApiError(Throwable throwable, HttpStatus status) {
        this(throwable.getMessage(), status);
    }
}

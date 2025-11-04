package com.final_exam_springboot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private int httpStatus;

    public static <T> ResponseWrapper<T> success(T data, String message, HttpStatus status) {
        return new ResponseWrapper<>(true, message, data, status.value());
    }
    public static <T> ResponseWrapper<T> success(T data, String message) {
        return new ResponseWrapper<>(true, message, data, HttpStatus.OK.value());
    }
    public static <T> ResponseWrapper<T> error(String message, HttpStatus status) {
        return new ResponseWrapper<>(false, message, null, status.value());
    }
}

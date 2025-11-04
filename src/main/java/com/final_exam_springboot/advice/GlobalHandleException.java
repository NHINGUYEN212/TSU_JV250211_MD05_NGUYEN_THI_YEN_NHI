package com.final_exam_springboot.advice;

import com.final_exam_springboot.exception.HttpConflict;
import com.final_exam_springboot.exception.HttpNotFound;
import com.final_exam_springboot.model.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.builder()
                .success(false)
                .message("MethodArgumentNotValidException")
                .data(errors)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    @ExceptionHandler(HttpConflict.class)
    public ResponseEntity<?> handleHttpConflict(HttpConflict e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseWrapper.builder()
                        .success(false)
                        .message("HttpConflict")
                        .data(e.getMessage())
                        .httpStatus(HttpStatus.CONFLICT.value())
                        .build()
        );
    }
    @ExceptionHandler(HttpNotFound.class)
    public ResponseEntity<?> handleHttpNotFound(HttpNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseWrapper.builder()
                        .success(false)
                        .message("Not Found")
                        .data(e.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND.value())
                        .build()
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseWrapper.builder()
                        .success(false)
                        .message("Đã có lỗi xảy ra ở server")
                        .data(e.getMessage())
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }
}


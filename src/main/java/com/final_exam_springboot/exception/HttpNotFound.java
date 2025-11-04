package com.final_exam_springboot.exception;

public class HttpNotFound extends RuntimeException {
    public HttpNotFound(String message) {
        super(message);
    }
}

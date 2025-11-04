package com.final_exam_springboot.exception;

public class HttpConflict extends RuntimeException{
    public HttpConflict(String message){
        super(message);
    }
}


package com.nsi.clonebin.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return "error";
    }
}

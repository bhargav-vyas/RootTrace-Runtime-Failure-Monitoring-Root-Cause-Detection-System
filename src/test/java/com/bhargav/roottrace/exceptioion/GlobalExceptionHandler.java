package com.bhargav.roottrace.exceptioion;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println("===== ROOTTRACE ERROR CAPTURED =====");
        System.out.println("Exception Type: " + ex.getClass().getName());
        System.out.println("Message: " + ex.getMessage());
        System.out.println("Time: " + LocalDateTime.now());

    }
}

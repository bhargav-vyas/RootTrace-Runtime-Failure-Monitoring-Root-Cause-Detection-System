package com.bhargav.roottrace.exception;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.sender.ErrorSenderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {
    private  final ErrorSenderService sender;

    public GlobalExceptionHandler(ErrorSenderService sender) {
        this.sender = sender;
    }
     @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request){
         ErrorEventDTO dto =new ErrorEventDTO();
         dto.setException(ex.getClass().getName());
         dto.setMessage(ex.getMessage());
         dto.setStackTrace(dto.getStackTrace());
         dto.setHttpMethod(request.getMethod());
         dto.setTimestamp(LocalDateTime.now());
         sender.sendError(dto);
}
private String getStackTrace(Exception ex){
    StringWriter sw= new StringWriter();
    ex.printStackTrace(new PrintWriter(sw));
    return sw.toString();
    }
}


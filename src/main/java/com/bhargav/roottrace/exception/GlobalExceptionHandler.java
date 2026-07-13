package com.bhargav.roottrace.exception;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.sender.ErrorSenderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorSenderService sender;

    public GlobalExceptionHandler(ErrorSenderService sender) {
        this.sender = sender;
    }

    // Handle missing URLs/static resources
    @ExceptionHandler(NoResourceFoundException.class)
    public void handleStaticResourceException(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        // Ignore browser favicon request only
        if (request.getRequestURI().equals("/favicon.ico")) {
            System.out.println(
                    "RootTrace ignored static resource: /favicon.ico"
            );
            return;
        }

        // Capture other missing URLs such as /test
        handleException(ex, request);
    }

    // Capture all other application exceptions
    @ExceptionHandler(Exception.class)
    public void handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorEventDTO dto = new ErrorEventDTO();

        dto.setExceptionType(ex.getClass().getName());
        dto.setMessage(ex.getMessage());
        dto.setStackTrace(getStackTrace(ex));

        dto.setHttpMethod(request.getMethod());
        dto.setHttpStatus(500);

        dto.setTimestamp(LocalDateTime.now());
        dto.setEndpoint(request.getRequestURI());

        sender.sendError(dto);
    }

    private String getStackTrace(Exception ex) {

        StringWriter sw = new StringWriter();

        ex.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }
}
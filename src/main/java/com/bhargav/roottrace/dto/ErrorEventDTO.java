package com.bhargav.roottrace.dto;

import java.time.LocalDateTime;

public class ErrorEventDTO {


    private String exception;
    private  String  message;
    private  String stackTrace;
    private String requestUrl;
    private String  httpMethod;
    private LocalDateTime timestamp;

    public void setException(String exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }



}

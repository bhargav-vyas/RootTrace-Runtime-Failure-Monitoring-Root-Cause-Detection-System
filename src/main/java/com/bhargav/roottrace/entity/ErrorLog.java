package com.bhargav.roottrace.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ErrorLog {



    @Id
    @GeneratedValue
    private Long  id;
    private String exceptionType;
    private String message;
    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;
    private String requestUrl;
    private String httpMethod;
    private String status ="OPEN";
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public Long getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}

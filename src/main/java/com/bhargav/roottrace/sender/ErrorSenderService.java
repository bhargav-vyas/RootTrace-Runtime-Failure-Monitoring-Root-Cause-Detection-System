package com.bhargav.roottrace.sender;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.properties.ErrorMonitorProperties;
import com.bhargav.roottrace.service.ErrorLogService;
import org.springframework.stereotype.Service;

@Service
public class ErrorSenderService {

    private final ErrorMonitorProperties properties;
    private final ErrorLogService service;

    public ErrorSenderService(ErrorMonitorProperties properties,
                              ErrorLogService service) {
        this.properties = properties;
        this.service = service;
    }

    public void sendError(ErrorEventDTO dto) {

        if (!properties.isEnable()) {
            return;
        }

        ErrorLog log = new ErrorLog();

        log.setExceptionType(dto.getException());
        log.setMessage(dto.getMessage());
        log.setStackTeace(dto.getStackTrace());
        log.setRequestUrl(dto.getRequestUrl());
        log.setHttpMethod(dto.getHttpMethod());

        service.save(log);

        System.out.println("Saved error in database");
    }
}
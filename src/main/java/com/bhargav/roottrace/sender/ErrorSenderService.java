package com.bhargav.roottrace.sender;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.properties.ErrorMonitorProperties;
import com.bhargav.roottrace.service.EmailService;
import com.bhargav.roottrace.service.ErrorLogService;
import org.springframework.stereotype.Service;

@Service
public class ErrorSenderService {

    private final ErrorMonitorProperties properties;
    private final ErrorLogService service;
    private final EmailService emailService;

    public ErrorSenderService(ErrorMonitorProperties properties,
                              ErrorLogService service, EmailService emailService) {
        this.properties = properties;
        this.service = service;
        this.emailService = emailService;
    }

    public void sendError(ErrorEventDTO dto) {

        if (!properties.isEnable()) {
            return;
        }

        ErrorLog log = new ErrorLog();

        log.setExceptionType(dto.getException());
        log.setMessage(dto.getMessage());
        log.setStackTrace(dto.getStackTrace());
        log.setRequestUrl(dto.getRequestUrl());
        log.setHttpMethod(dto.getHttpMethod());
//        if (dto.getException().contains("NullPointerException")) {
//            log.setSeverity("HIGH");
//        } else if (dto.getException().contains("ArithmeticException")) {
//            log.setSeverity("MEDIUM");
//        } else if (dto.getException().contains("OutOfMemoryError")) {
//            log.setSeverity("CRITICAL");
//        } else {
//            log.setSeverity("LOW");
//        }
        log.setSeverity("CRITICAL");
        if ("CRITICAL".equals(log.getSeverity())) {

            emailService.sendCriticalAlert(
                    "Critical Error Detected\n\n" +
                            "Exception: " + log.getExceptionType() + "\n\n" +
                            "Message: " + log.getMessage()
            );
        }

        service.save(log);

        System.out.println("Saved error in database");
    }
}



package com.bhargav.roottrace.sender;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.properties.ErrorMonitorProperties;

public class ErrorSenderService {

    private   final ErrorMonitorProperties properties;


    public ErrorSenderService(ErrorMonitorProperties properties) {
        this.properties = properties;
    }
    public void  sendError(ErrorEventDTO dto)
    {
        if(!properties.isEnable()){
            return;

        }
        System.out.println("=== RootTrace Error captured ===");
        System.out.println("Exception :" +dto.getException());
        System.out.println("Message:" +dto.getMessage());
        System.out.println("URL" + dto.getRequestUrl());
    }
}

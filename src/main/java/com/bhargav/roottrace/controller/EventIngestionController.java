package com.bhargav.roottrace.controller;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.sender.ErrorSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin("*")
public class EventIngestionController {

    private final ErrorSenderService sender;

    public EventIngestionController(ErrorSenderService sender) {
        this.sender = sender;
    }

    @PostMapping
    public ResponseEntity<Void> receiveEvent(
            @RequestBody ErrorEventDTO event) {

        System.out.println(">>> /api/events RECEIVED");

        sender.sendError(event);

        return ResponseEntity.accepted().build();
    }
}
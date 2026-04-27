package com.bhargav.roottrace.controller;

import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.service.ErrorLogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/errprs")
@CrossOrigin("*")
public class ErrorLogController {
    private final ErrorLogService service;


    public ErrorLogController(ErrorLogService service) {
        this.service = service;
    }
    @GetMapping
    public List<ErrorLog> getAllErrors(){
        return service.getAll();
    }

}

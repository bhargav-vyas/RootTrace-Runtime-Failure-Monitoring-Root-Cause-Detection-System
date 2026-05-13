package com.bhargav.roottrace.controller;

import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.service.ErrorLogService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/errors")
@CrossOrigin("*")
public class ErrorLogController {
    private final ErrorLogService service;


    public ErrorLogController(ErrorLogService service) {
        this.service = service;
    }

    @GetMapping
    public List<ErrorLog> getAllErrors() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public String deletError(@PathVariable Long id) {
          service.deletById(id);
          return "Error delleted successfully";
    }
    @GetMapping("/type/{type}")
    public  List<ErrorLog> getByExceptionType(@PathVariable String type){
        return service.getByExceptionType(type);
    }
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalErrors", service.getTotalErrors());
        return stats;

    }
}

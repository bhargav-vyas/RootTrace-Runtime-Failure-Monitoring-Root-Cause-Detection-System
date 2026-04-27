package com.bhargav.roottrace.service;


import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorLogService {
    private final ErrorLogRepository repository;

    public ErrorLogService(ErrorLogRepository repository) {
        this.repository = repository;
    }
    public ErrorLog save(ErrorLog errorLog) {
        return repository.save(errorLog);
    }
    public List<ErrorLog> getAll() {
        return repository.findAll();
    }
}

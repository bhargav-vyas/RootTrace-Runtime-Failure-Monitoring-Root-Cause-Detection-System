package com.bhargav.roottrace.service;

import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorLogService {

    private final ErrorLogRepository repository;

    public ErrorLogService(ErrorLogRepository repository) {
        this.repository = repository;
    }

    public List<ErrorLog> getAll() {
        return repository.findAll();
    }

    public ErrorLog save(ErrorLog errorLog) {
        return repository.save(errorLog);
    }

    public Optional<ErrorLog> getByFingerprint(String fingerprint) {
        return repository.findByFingerprint(fingerprint);
    }

    public List<ErrorLog> getByExceptionType(String exceptionType) {
        return repository.findByExceptionType(exceptionType);
    }

    public long getTotalErrors() {
        return repository.count();
    }

    public void deletById(Long id) {
        repository.deleteById(id);
    }

    public ErrorLog markResolved(Long id) {

        ErrorLog errorLog = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Error log not found with id: " + id)
                );

        errorLog.setStatus("RESOLVED");

        return repository.save(errorLog);
    }
}
package com.bhargav.roottrace.repository;

import com.bhargav.roottrace.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ErrorLogRepository  extends JpaRepository<ErrorLog ,Long> {

    List<ErrorLog> findByExceptionType(String type);
}

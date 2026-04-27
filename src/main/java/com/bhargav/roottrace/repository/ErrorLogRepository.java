package com.bhargav.roottrace.repository;

import com.bhargav.roottrace.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository  extends JpaRepository<ErrorLog ,Long> {
}

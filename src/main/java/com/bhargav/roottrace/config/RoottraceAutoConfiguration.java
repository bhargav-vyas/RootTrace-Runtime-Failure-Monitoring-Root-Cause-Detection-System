package com.bhargav.roottrace.config;

import com.bhargav.roottrace.properties.ErrorMonitorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ErrorMonitorProperties.class)
public class RoottraceAutoConfiguration {
}
 
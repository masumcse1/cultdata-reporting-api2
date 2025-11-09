package com.cultdata.report.api.rest;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Hidden
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CultDataReportApiStatusController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/cultdata-report/status")
    public Map<String, Object> getCultDataReportApiStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", appName);
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", "CultData Report API server is running successfully");
        log.info("GET /api/v1/cultdata-report/status called");
        return response;
    }
}

//http://localhost:8084/api/v1/cultdata-report/status
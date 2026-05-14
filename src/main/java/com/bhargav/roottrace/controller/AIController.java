package com.bhargav.roottrace.controller;

import com.bhargav.roottrace.service.OpenAIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin("*")
public class AIController {

    private final OpenAIService openAIService;

    public AIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/analyze")
    public String analyze(@RequestBody Map<String, String> body) {

        String stackTrace = body.get("stackTrace");

        return openAIService.analyzeError(stackTrace);
    }
}
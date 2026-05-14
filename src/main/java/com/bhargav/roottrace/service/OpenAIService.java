package com.bhargav.roottrace.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create();

    public String analyzeError(String stackTrace) {

        try {

            String prompt = """
                Analyze this Java runtime error.

                Give:
                1. Root cause
                2. Severity
                3. Suggested fix

                Error:
                """ + stackTrace;

            Map<String,Object> request = new HashMap<>();

            request.put("model","gpt-4o-mini");

            request.put(
                    "messages",
                    List.of(
                            Map.of(
                                    "role","user",
                                    "content",prompt
                            )
                    )
            );

            request.put("temperature",0.3);

            String response = webClient.post()
                    .uri("https://api.openai.com/v1/chat/completions")
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + apiKey
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("OPENAI RESPONSE:");
            System.out.println(response);

            return response;

        } catch(Exception e){

            e.printStackTrace();

            return "ERROR: " + e.getMessage();
        }
    }
}
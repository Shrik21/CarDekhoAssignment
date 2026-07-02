package com.cardekho.advisor.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "app", "AI Car Advisor API",
                "status", "running",
                "endpoints", Map.of(
                        "cars", "/api/cars",
                        "carById", "/api/cars/{id}",
                        "recommendations", "POST /api/recommendations"
                )
        );
    }
}

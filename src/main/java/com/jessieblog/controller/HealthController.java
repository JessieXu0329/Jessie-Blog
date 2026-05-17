package com.jessieblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@RestController
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
            "ok", true,
            "service", "jessie-blog-api"
        );
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of("ok", true);
    }

    @GetMapping("/health/db")
    public Map<String, Object> databaseHealth() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            return Map.of(
                "ok", connection.isValid(2),
                "database", connection.getCatalog()
            );
        }
    }
}

package com.cognizant.config;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

    private final List<String> openEndpoints = List.of(
            "api/user/login",
            "api/user/register"
    );

    public boolean isSecured(String path) {
        return openEndpoints.stream().noneMatch(path::startsWith);
    }
}

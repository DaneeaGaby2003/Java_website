package com.example.orders.web;

import com.example.orders.config.OrderAppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigInfoController {
    private final OrderAppProperties props;

    public ConfigInfoController(OrderAppProperties props){ this.props = props; }

    @GetMapping("/info/config")
    public Map<String, Object> info() {
        String active = System.getProperty(
                "spring.profiles.active",
                System.getenv().getOrDefault("SPRING_PROFILES_ACTIVE", "dev")
        );
        return Map.of(
                "defaultPageSize", props.getDefaultPageSize(),
                "maxPageSize",     props.getMaxPageSize(),
                "activeProfile",   active
        );
    }
}

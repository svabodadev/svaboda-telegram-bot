package com.svaboda.telegram.healthservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HealthController {

    @GetMapping("/_ready")
    String status() {
        return "OK";
    }
}

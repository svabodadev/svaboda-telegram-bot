package com.svaboda.telegram.diagnostic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HealthResource {

    @GetMapping("/_ready")
    String status() {
        return "OK";
    }
}

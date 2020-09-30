package com.svaboda.telegram.healthservice;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HealthController {

    @GetMapping("/")
    Status status() {
        return Status.ok();
    }

    @Value
    static class Status {

        String status;

        private static Status ok() {
            return new Status("OK");
        }
    }
}

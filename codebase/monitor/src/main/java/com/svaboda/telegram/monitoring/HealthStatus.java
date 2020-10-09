package com.svaboda.telegram.monitoring;

import lombok.Value;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Value
class HealthStatus {
    int statusCode;
    String response;
    String serviceUrl;
    Instant timestamp;

    static HealthStatus from(ResponseEntity<String> response, String url) {
        return new HealthStatus(
                response.getStatusCodeValue(),
                response.getBody(),
                url,
                Instant.now()
        );
    }
}

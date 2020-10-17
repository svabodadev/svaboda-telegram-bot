package com.svaboda.monitor.monitoring;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
class HealthChecking {

    private final WebClient webClient;

    Mono<HealthStatus> check(String url) {
        return webClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(String.class)
                        .map(response -> HealthStatus.from(response, url));
    }
}

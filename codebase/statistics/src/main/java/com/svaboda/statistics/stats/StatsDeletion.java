package com.svaboda.statistics.stats;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
class StatsDeletion {

    private final WebClient webClient;

    Try<ResponseEntity<Void>> deleteFrom(String url) {
        return Try.of(() -> webClient.delete()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity()
                .block()
        );
    }
}

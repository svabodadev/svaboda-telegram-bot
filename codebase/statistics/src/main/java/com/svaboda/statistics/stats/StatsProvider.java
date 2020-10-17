package com.svaboda.statistics.stats;

import com.svaboda.storage.stats.Statistics;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@AllArgsConstructor
class StatsProvider {

    private final WebClient webClient;

    Try<ResponseEntity<Statistics>> statsFrom(String url) {
        return Try.of(() -> webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Statistics.class)
                .block()
        );
    }
}

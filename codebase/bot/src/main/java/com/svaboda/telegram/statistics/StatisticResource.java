package com.svaboda.telegram.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class StatisticResource {

    private final StatisticsProvider statisticsProvider;

    @GetMapping("/_stats")
    Statistics status() {
        return statisticsProvider.provide().get();
    }
}

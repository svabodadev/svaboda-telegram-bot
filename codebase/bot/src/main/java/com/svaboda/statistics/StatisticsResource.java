package com.svaboda.statistics;

import com.svaboda.utils.Endpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class StatisticsResource {

    private final StatisticsProvider statisticsProvider;

    @GetMapping(Endpoints.STATS)
    Statistics statistics() {
        return statisticsProvider.provide().get();
    }
}

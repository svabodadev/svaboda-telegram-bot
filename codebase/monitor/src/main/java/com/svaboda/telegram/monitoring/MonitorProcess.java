package com.svaboda.telegram.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
class MonitorProcess {

    private final HealthChecking healthChecking;
    private final String serviceUrl;

    void process() {
        healthChecking.check(serviceUrl)
                .doOnSuccess(status -> log.info("Service responded with status: {}", status))
                .doOnError(failure -> log.error("Error occurred on checking status for url {}", serviceUrl))
                .block();
    }

}

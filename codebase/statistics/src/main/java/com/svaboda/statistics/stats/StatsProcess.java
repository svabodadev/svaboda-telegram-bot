package com.svaboda.statistics.stats;

import com.svaboda.storage.failureinfo.FailureInfo;
import com.svaboda.storage.failureinfo.FailureInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
class StatsProcess {

    private final String serviceUrl;
    private final StatsOperation statsOperation;
    private final FailureInfoRepository failureInfoRepository;

    void process() {
        statsOperation.process(serviceUrl)
                .peek(stats -> log.info("Success on processing statistics from {}", serviceUrl))
                .onFailure(this::handleFailure);
    }

    private void handleFailure(Throwable throwable) {
        final var errorMsg = "Error occurred on processing statistics from url " + serviceUrl;
        final var failure = new RuntimeException(errorMsg, throwable);
        log.error(errorMsg, failure);
        failureInfoRepository.save(FailureInfo.from(failure))
                .onFailure(ex -> log.error("Error occurred on handling failure", ex));
    }

}

package com.svaboda.statistics.stats;

import com.svaboda.storage.failureinfo.FailureInfo;
import com.svaboda.storage.failureinfo.FailureInfoRepository;
import com.svaboda.storage.stats.StatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@Slf4j
class StatsProcess {

    private final StatsProvider statsProvider;
    private final String serviceUrl;
    private final StatsRepository statsRepository;
    private final FailureInfoRepository failureInfoRepository;

    void process() {
        statsProvider.statsFrom(serviceUrl)
                .map(ResponseEntity::getBody)
                .flatMap(statsRepository::save)
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

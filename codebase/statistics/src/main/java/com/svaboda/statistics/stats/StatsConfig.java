package com.svaboda.statistics.stats;

import com.svaboda.storage.failureinfo.FailureInfoRepository;
import com.svaboda.storage.stats.StatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({StatsProperties.class})
class StatsConfig {

    @Bean
    TaskScheduler taskScheduler(StatsProperties statsProperties,
                                WebClient webClient,
                                StatsRepository statsRepository,
                                FailureInfoRepository failureInfoRepository) {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(statsProperties.servicesUrls().size());
        scheduler.initialize();
        schedule(scheduler, statsProperties, webClient, statsRepository, failureInfoRepository);
        return scheduler;
    }

    private void schedule(ThreadPoolTaskScheduler scheduler,
                          StatsProperties statsProperties,
                          WebClient webClient,
                          StatsRepository statsRepository,
                          FailureInfoRepository failureInfoRepository) {
        statsProperties.servicesUrls().forEach(url -> {
                    log.info("Scheduling stats process for {}", url);
                    final var stats = statsProcess(webClient, url, statsRepository, failureInfoRepository);
                    scheduler.scheduleWithFixedDelay(stats::process, statsProperties.intervalSec());
                    log.info("stats process for {} scheduled", url);
                }
        );
    }

    private StatsProcess statsProcess(WebClient webClient,
                                      String url,
                                      StatsRepository statsRepository,
                                      FailureInfoRepository failureInfoRepository) {
        return new StatsProcess(new StatsProvider(webClient), url, statsRepository, failureInfoRepository);
    }
}

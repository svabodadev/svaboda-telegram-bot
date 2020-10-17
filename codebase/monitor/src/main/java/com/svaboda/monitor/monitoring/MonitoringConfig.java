package com.svaboda.monitor.monitoring;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({MonitorProperties.class})
class MonitoringConfig {

    @Bean
    TaskScheduler taskScheduler(MonitorProperties monitorProperties, WebClient webClient) {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(monitorProperties.servicesUrls().size());
        scheduler.initialize();
        schedule(scheduler, monitorProperties, webClient);
        return scheduler;
    }

    private void schedule(ThreadPoolTaskScheduler scheduler, MonitorProperties monitorProperties, WebClient webClient) {
        monitorProperties.servicesUrls().forEach(url -> {
                    log.info("Scheduling monitoring for {}", url);
                    final var monitor = monitorProcess(webClient, url);
                    scheduler.scheduleWithFixedDelay(monitor::process, Duration.ofSeconds(monitorProperties.intervalSec()));
                    log.info("Monitoring for {} scheduled", url);
                }
        );
    }

    private MonitorProcess monitorProcess(WebClient webClient, String url) {
        return new MonitorProcess(new HealthChecking(webClient), url);
    }

}

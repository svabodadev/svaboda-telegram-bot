package com.svaboda.telegram.statistics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfiguration {

    private final CachedStatistics cachedStatistics = new CachedStatistics();

    @Bean
    public StatisticsProvider statisticsProvider() {
        return cachedStatistics;
    }

    @Bean
    public StatisticsHandler statisticsHandler() {
        return cachedStatistics;
    }
}

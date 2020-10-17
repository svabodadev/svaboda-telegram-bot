package com.svaboda.bot.stats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfiguration {

    @Bean(name = {"statisticsHandler", "statisticsProvider", "statisticsHandler"})
    public StatisticsHandler statisticsHandler() {
        return new CachedStatistics();
    }
}

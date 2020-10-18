package com.svaboda.bot.stats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfiguration {

    @Bean(name = {"statisticsRegistration", "statisticsProvider", "statisticDeletion", "statisticsHandler"})
    public StatisticsHandler statisticsHandler() {
        return new CachedStatistics();
    }
}

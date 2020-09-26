package com.svaboda.telegram.bot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({BotProperties.class})
class BotConfig {

    @Bean
    Bot bot(BotProperties botProperties) {
        return new Bot(botProperties);
    }
}

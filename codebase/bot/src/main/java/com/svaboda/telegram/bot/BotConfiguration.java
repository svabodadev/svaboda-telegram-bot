package com.svaboda.telegram.bot;

import com.svaboda.telegram.domain.ResourceProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({BotProperties.class})
class BotConfiguration {

    @Bean
    Bot bot(BotProperties botProperties) {
        return new Bot(botProperties);
    }

    @Bean
    CommandHandler commandHandler(ResourceProvider<String> resourceProvider) {
        return new SimpleCommandHandler(resourceProvider);
    }

}

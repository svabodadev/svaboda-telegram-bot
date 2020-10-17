package com.svaboda.bot.bot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@EnableConfigurationProperties({BotProperties.class})
class BotConfiguration {

    @Bean
    Bot bot(BotProperties properties, MessageProcessor messageProcessor) {
        return new Bot(botOptions(properties), properties, messageProcessor);
    }

    private DefaultBotOptions botOptions(BotProperties botProperties) {
        final var options = new DefaultBotOptions();
        options.setBaseUrl(botProperties.baseUrl());
        return options;
    }

}

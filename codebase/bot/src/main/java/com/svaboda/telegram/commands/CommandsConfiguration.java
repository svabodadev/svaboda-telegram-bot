package com.svaboda.telegram.commands;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CommandsProperties.class})
class CommandsConfiguration {

    @Bean
    Commands commands(CommandsProperties properties) {
        return new CommandsContainer(properties.commands());
    }

}

package com.svaboda.telegram.commands;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CommandsProperties.class})
public class CommandsConfiguration {

    @Bean
    public Commands commands(CommandsProperties properties) {
        return new CommandsContainer(properties.commands());
    }

}

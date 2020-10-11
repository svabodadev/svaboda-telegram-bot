package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.bot.MessageProcessor;
import com.svaboda.telegram.commands.Commands;
import com.svaboda.telegram.commands.CommandsProperties;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.ResourcesProperties;
import com.svaboda.telegram.statistics.StatisticsHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ResourcesProperties.class})
class FileResourcesConfiguration {

    @Bean
    MessageProcessor commandHandler(
            ResourcesProperties resourcesProperties,
            StatisticsHandler statisticsHandler,
            CommandsProperties commandsProperties,
            Commands commands) {
        return new SimpleMessageProcessor(
                resourceProvider(resourcesProperties, commandsProperties), statisticsHandler, commands);
    }

    private ResourceProvider<String> resourceProvider(ResourcesProperties resourcesProperties,
                                                      CommandsProperties commandsProperties) {
        return new CachedFileResourceProvider(
                new TextFileResourceReader(resourcesProperties),
                new TextTransformer(resourcesProperties, commandsProperties)
        );
    }

}

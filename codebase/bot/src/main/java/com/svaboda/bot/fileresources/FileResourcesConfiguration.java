package com.svaboda.bot.fileresources;

import com.svaboda.bot.bot.MessageProcessor;
import com.svaboda.bot.commands.Commands;
import com.svaboda.bot.commands.CommandsProperties;
import com.svaboda.bot.domain.ResourceProvider;
import com.svaboda.bot.domain.ResourcesProperties;
import com.svaboda.bot.stats.StatisticsRegistration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ResourcesProperties.class})
class FileResourcesConfiguration {

    @Bean
    MessageProcessor commandHandler(
            ResourcesProperties resourcesProperties,
            StatisticsRegistration statisticsRegistration,
            CommandsProperties commandsProperties,
            Commands commands) {
        return new SimpleMessageProcessor(
                resourceProvider(resourcesProperties, commandsProperties), statisticsRegistration, commands);
    }

    private ResourceProvider<String> resourceProvider(ResourcesProperties resourcesProperties,
                                                      CommandsProperties commandsProperties) {
        return new CachedFileResourceProvider(
                new TextFileResourceReader(resourcesProperties),
                new TextTransformer(resourcesProperties, commandsProperties)
        );
    }

}

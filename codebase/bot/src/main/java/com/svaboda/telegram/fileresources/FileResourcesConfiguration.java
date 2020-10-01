package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.bot.MessageProcessor;
import com.svaboda.telegram.commands.Commands;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.ResourcesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ResourcesProperties.class})
class FileResourcesConfiguration {

    @Bean
    MessageProcessor commandHandler(ResourcesProperties resourcesProperties, Commands commands) {
        return new SimpleMessageProcessor(resourceProvider(resourcesProperties), commands);
    }

    private ResourceProvider<String> resourceProvider(ResourcesProperties resourcesProperties) {
        return new CachedFileResourceProvider(new TextFileResourceReader(resourcesProperties), resourcesProperties);
    }

}

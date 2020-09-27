package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.domain.ResourceProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FileResourcesProperties.class})
class FileResourcesConfiguration {

    @Bean
    ResourceProvider<String> resourceProvider(FileResourcesProperties fileResourcesProperties) {
        return new CachedFileResourceProvider(new TextFileResourceReader(fileResourcesProperties.path()));
    }

}

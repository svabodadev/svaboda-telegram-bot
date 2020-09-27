package com.svaboda.telegram.fileresources;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.google.common.base.Preconditions.checkNotNull;

@ConfigurationProperties(prefix = "env.telegram.texts")
@ConstructorBinding
@Value
class FileResourcesProperties {

    String path;

    FileResourcesProperties(String path) {
        this.path = checkNotNull(path);
    }
}
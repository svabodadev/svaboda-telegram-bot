package com.svaboda.telegram.fileresources;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;

@ConfigurationProperties(prefix = "env.telegram.texts")
@ConstructorBinding
@Value
class FileResourcesProperties {

    String path;
    String fileExtension;

    FileResourcesProperties(String path, String fileExtension) {
        this.path = notEmpty(path);
        this.fileExtension = notEmpty(fileExtension);
    }
}
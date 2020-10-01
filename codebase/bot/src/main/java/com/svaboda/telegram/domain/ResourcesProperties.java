package com.svaboda.telegram.domain;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;
import static com.svaboda.telegram.support.ArgsValidation.positive;

@ConfigurationProperties(prefix = "env.telegram.texts")
@ConstructorBinding
@Value
public class ResourcesProperties {

    String path;
    String fileExtension;
    int maxResourceSize;
    String topicEnrichmentLine;
    String goToArticleLine;

    public ResourcesProperties(String path, String fileExtension, Integer maxResourceSize, String topicEnrichmentLine,
                        String goToArticleLine) {
        this.path = notEmpty(path);
        this.fileExtension = notEmpty(fileExtension);//todo to be removed - content should be read from google docs
        this.maxResourceSize = positive(maxResourceSize);
        this.topicEnrichmentLine = "\n"+notEmpty(topicEnrichmentLine);
        this.goToArticleLine = "\n"+notEmpty(goToArticleLine)+" ";
    }

}
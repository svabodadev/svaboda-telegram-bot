package com.svaboda.storage.mongo;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.svaboda.utils.ArgsValidation.notEmpty;

@ConfigurationProperties(prefix = "env.db")
@ConstructorBinding
@Value
class MongoProperties {
    String url;

    MongoProperties(String url) {
        this.url = notEmpty(url);
    }

}

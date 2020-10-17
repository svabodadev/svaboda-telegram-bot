package com.svaboda.telegram.dao;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.svaboda.telegram.utils.ArgsValidation.notEmpty;

@ConfigurationProperties(prefix = "env.db")
@ConstructorBinding
@Value
class DatabaseProperties {
    String url;

    DatabaseProperties(String url) {
        this.url = notEmpty(url);
    }

}

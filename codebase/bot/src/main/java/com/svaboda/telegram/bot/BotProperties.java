package com.svaboda.telegram.bot;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.svaboda.telegram.utils.ArgsValidation.notEmpty;

@ConfigurationProperties(prefix = "env.telegram.bot")
@ConstructorBinding
@Value
class BotProperties {

    String name;
    String token;
    String baseUrl;

    BotProperties(String name, String token, String baseUrl) {
        this.name = notEmpty(name);
        this.token = notEmpty(token);
        this.baseUrl = notEmpty(baseUrl);
    }
}

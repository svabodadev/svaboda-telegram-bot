package com.svaboda.telegram.bot;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.google.common.base.Preconditions.checkNotNull;

@ConfigurationProperties(prefix = "env.telegram.bot")
@ConstructorBinding
@Value
class BotProperties {

    String name;
    String token;
    String baseUrl;//todo to check
    int creatorId;

    BotProperties(String name, String token, String baseUrl, Integer creatorId) {
        this.name = checkNotNull(name);
        this.token = checkNotNull(token);
        this.baseUrl = checkNotNull(baseUrl);
        this.creatorId = checkNotNull(creatorId);
    }
}
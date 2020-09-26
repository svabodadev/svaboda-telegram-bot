package com.svaboda.telegram.bot;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConfigurationProperties(prefix = "env.telegram.bot")
@ConstructorBinding
@Value
class BotProperties {

    String name;
    String token;
    String baseUrl;//todo to check
    int creatorId;

}
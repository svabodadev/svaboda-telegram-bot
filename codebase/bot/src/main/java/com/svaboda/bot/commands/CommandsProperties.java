package com.svaboda.bot.commands;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "env")
@ConstructorBinding
@Value
public class CommandsProperties {
    List<Command> commands;
}

package com.svaboda.telegram.commands;

import lombok.Value;

import java.util.Optional;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;

@Value
public class Command {
    public final static Command TOPICS_INSTANCE = new Command("/topics", "topics", null);
    private final static String PREFIX = "/";

    String name;
    String resourceId;
    String externalLink;

    Command(String name, String resourceId, String externalLink) {
        this.name = enrichWithPrefix(notEmpty(name));
        this.resourceId = notEmpty(resourceId);
        this.externalLink = externalLink;
    }

    public boolean isTopicsCommand() {
        return this.equals(TOPICS_INSTANCE);
    }

    public Optional<String> externalLink() {
        return Optional.ofNullable(externalLink);
    }

    private String enrichWithPrefix(String name) {
        return name.startsWith(PREFIX) ? name : PREFIX+name;
    }

}
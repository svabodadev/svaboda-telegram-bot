package com.svaboda.telegram.commands;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Optional;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;

@Data
public class Command {
    public final static Command TOPICS_INSTANCE = new Command("/topics", "topics");
    private final static String PREFIX = "/";

    @Setter(AccessLevel.NONE)
    private final String name;
    @Setter(AccessLevel.NONE)
    private final String resourceId;
    @Setter(AccessLevel.NONE)
    private String externalLink = null;

    Command(String name, String resourceId, String externalLink) {
        this(name, resourceId);
        this.externalLink = externalLink;
    }

    private Command(String name, String resourceId) {
        this.name = enrichWithPrefix(notEmpty(name));
        this.resourceId = notEmpty(resourceId);
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
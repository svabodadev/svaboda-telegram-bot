package com.svaboda.telegram.commands;

import lombok.Value;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;

@Value
public class Command {
    private final static String PREFIX = "/";
    String name;
    String resourceId;

    public Command(String name, String resourceId) {
        this.name = enrichWithPrefix(notEmpty(name));
        this.resourceId = notEmpty(resourceId);
    }

    private String enrichWithPrefix(String name) {
        return name.startsWith(PREFIX) ? name : PREFIX+name;
    }

}
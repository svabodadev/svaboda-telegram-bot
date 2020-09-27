package com.svaboda.telegram.commands;

import lombok.Value;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;

@Value
public class Command {
    private final static String PREFIX = "/";
    String name;
    String resourceId;

    public Command(String name, String resourceId) {
        this.name = PREFIX + notEmpty(name);
        this.resourceId = notEmpty(resourceId);
    }

}
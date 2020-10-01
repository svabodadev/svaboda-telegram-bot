package com.svaboda.telegram.commands;

public interface Commands {

    String TOPICS_COMMAND_NAME = "/topics";
    String TOPICS_COMMAND_RESOURCE_ID = "topics";

    default Command topicsCommand() {
        return new Command(TOPICS_COMMAND_NAME, TOPICS_COMMAND_RESOURCE_ID);
    }

    Command byName(String commandName);
}

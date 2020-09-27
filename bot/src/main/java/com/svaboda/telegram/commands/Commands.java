package com.svaboda.telegram.commands;

public interface Commands {

    default Command defaultCommand() {
        return new Command("main", "main");
    }

    Command byName(String commandName);
}

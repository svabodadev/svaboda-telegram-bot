package com.svaboda.telegram.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CommandsContainer implements Commands {

    private static final Map<String,Command> commandsCache = new ConcurrentHashMap<>();

    CommandsContainer(List<Command> commands) {
        commands.forEach(command -> commandsCache.computeIfAbsent(command.name(), __ -> command));
    }

    @Override
    public Command byName(String commandName) {
        return commandsCache.getOrDefault(commandName, defaultCommand());
    }

}

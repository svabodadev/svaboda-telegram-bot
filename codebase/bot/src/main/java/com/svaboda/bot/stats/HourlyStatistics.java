package com.svaboda.bot.stats;

import com.svaboda.bot.commands.Command;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Value
public class HourlyStatistics {
    private static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH");

    String generatedAt;
    Map<String,Integer> commandsCalls = Collections.synchronizedMap(new HashMap<>());
    Set<Long> uniqueChats = Collections.synchronizedSet(new HashSet<>());

    private HourlyStatistics(String generatedAt, Command command, long chatId) {
        this.generatedAt = generatedAt;
        this.commandsCalls.put(command.name(), 1);
        this.uniqueChats.add(chatId);
    }

    public static HourlyStatistics create(LocalDateTime timestamp, Command command, long chatId) {
        return new HourlyStatistics(timestamp.format(HOUR_FORMAT), command, chatId);
    }

    public void register(Command command, long chatId) {
        commandsCalls.putIfAbsent(command.name(), 0);
        commandsCalls.computeIfPresent(command.name(), (__,value) -> value + 1);
        uniqueChats.add(chatId);
    }

    public boolean isFromSameHour(LocalDateTime localDateTime) {
        return generatedAt.equals(localDateTime.format(HOUR_FORMAT));
    }

    public boolean isBefore(LocalDateTime localDateTime) {
        return generatedAt.compareTo(localDateTime.format(HOUR_FORMAT)) < 0;
    }
}

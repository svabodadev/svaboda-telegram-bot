package com.svaboda.bot.stats;

import com.svaboda.bot.commands.Command;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Value
public class Statistics {

    int uniqueChats;
    List<CommandCallCount> statistics;
    Instant generatedAt = Instant.now();

    @Value
    public static class CommandCallCount {
        String command;
        long hitCount;

        public static CommandCallCount from(Map.Entry<Command, AtomicLong> entry) {
            return new CommandCallCount(entry.getKey().name(), entry.getValue().longValue());
        }
    }

}

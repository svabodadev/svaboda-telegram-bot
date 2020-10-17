package com.svaboda.telegram.dao.statistics;

import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
public class Stats {

    int uniqueChats;
    List<CommandCallCount> statistics;
    Instant generatedAt;

    @Value
    public static class CommandCallCount {
        String command;
        long hitCount;
    }

}

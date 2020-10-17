package com.svaboda.storage.stats;

import lombok.Data;

import java.util.List;

@Data
public class Statistics {

    private int uniqueChats;
    private List<CommandCallCount> statistics;
    private String generatedAt;

    @Data
    public static class CommandCallCount {
        String command;
        long hitCount;
    }

}

package com.svaboda.storage.stats;

import lombok.Data;

import java.util.*;

//todo handle uniqueChats ids differently
@Data
public class Statistics {
    String generatedAt;
    Map<String,Integer> commandsCalls;
    Set<Long> uniqueChats;
}

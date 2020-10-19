package com.svaboda.storage.stats;

import io.vavr.control.Try;

import java.util.List;

public interface StatsRepository {
    Try<Void> save(Statistics statistics);
    Try<Void> saveAll(List<Statistics> statistics);
    Try<List<Stats>> loadAllByTimestamps(List<String> timestamps);
}

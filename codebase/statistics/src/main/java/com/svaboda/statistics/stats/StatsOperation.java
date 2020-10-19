package com.svaboda.statistics.stats;

import io.vavr.control.Try;

public interface StatsOperation {
    Try<Void> process(String targetServiceUrl);
}

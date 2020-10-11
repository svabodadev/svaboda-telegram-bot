package com.svaboda.telegram.statistics;

import io.vavr.control.Try;

public interface StatisticsProvider {
    Try<Statistics> provide();
}

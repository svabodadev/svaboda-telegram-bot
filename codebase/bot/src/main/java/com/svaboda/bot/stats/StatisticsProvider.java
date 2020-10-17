package com.svaboda.bot.stats;

import io.vavr.control.Try;

public interface StatisticsProvider {
    Try<Statistics> provide();
}

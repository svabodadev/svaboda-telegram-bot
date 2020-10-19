package com.svaboda.bot.stats;

import io.vavr.control.Try;

import java.time.LocalDateTime;

public interface StatisticDeletion {
    Try<Void> deleteBefore(LocalDateTime timestamp);
}

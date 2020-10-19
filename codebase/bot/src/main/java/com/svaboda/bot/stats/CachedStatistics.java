package com.svaboda.bot.stats;

import com.svaboda.bot.commands.Command;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
class CachedStatistics implements StatisticsHandler {

    private final Stack<HourlyStatistics> archived = new Stack<>();

    @Override
    public Try<Void> register(Command command, Long chatId) {
        return Try.run(() -> registerHourlyStatistics(command, chatId));
    }

    @Override
    public Try<List<HourlyStatistics>> provide() {
        return Try.of(() -> new ArrayList<>(archived));
    }

    @Override
    public Try<Void> deleteBefore(LocalDateTime timestamp) {
        return Try.run(() -> {
            final var copy = new ArrayList<>(archived);
            archived.clear();
            copy.forEach(statistics -> {
                if (!statistics.isBefore(timestamp)) {
                    archived.push(statistics);
                }
            });
        })
        .onFailure(ex -> log.error("Error occurred on removing archived stats created before {}", timestamp, ex));
    }

    private void registerHourlyStatistics(Command command, Long chatId) {
        final var now = LocalDateTime.now();
        if (archived.isEmpty()) {
            archived.push(HourlyStatistics.create(now, command, chatId));
        } else {

            if (archived.peek().isFromSameHour(now)) {
                archived.peek().register(command, chatId);
            } else {
                archived.push(HourlyStatistics.create(now, command, chatId));
            }
        }
    }
}
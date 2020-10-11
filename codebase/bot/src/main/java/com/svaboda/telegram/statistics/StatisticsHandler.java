package com.svaboda.telegram.statistics;

import com.svaboda.telegram.commands.Command;
import io.vavr.control.Try;

public interface StatisticsHandler {
    Try<Void> register(Command command, Long chatId);
}

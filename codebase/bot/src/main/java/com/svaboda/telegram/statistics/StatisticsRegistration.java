package com.svaboda.telegram.statistics;

import com.svaboda.telegram.commands.Command;
import io.vavr.control.Try;

public interface StatisticsRegistration {
    Try<Void> register(Command command, Long chatId);
}

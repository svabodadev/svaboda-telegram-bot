package com.svaboda.statistics;

import com.svaboda.bot.commands.Command;
import io.vavr.control.Try;

public interface StatisticsRegistration {
    Try<Void> register(Command command, Long chatId);
}

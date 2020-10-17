package com.svaboda.bot.domain;

import com.svaboda.bot.commands.Command;
import io.vavr.control.Try;

public interface ResourceProvider<T> {

    Try<TelegramResource<T>> provideBy(Command command);

}

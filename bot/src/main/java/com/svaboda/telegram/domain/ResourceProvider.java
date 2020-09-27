package com.svaboda.telegram.domain;

import com.svaboda.telegram.commands.Command;
import io.vavr.control.Try;

public interface ResourceProvider<T> {

    Try<TelegramResource<T>> provideBy(Command command);

}

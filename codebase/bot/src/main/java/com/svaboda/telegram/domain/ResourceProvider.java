package com.svaboda.telegram.domain;

import io.vavr.control.Try;

public interface ResourceProvider<T> {

    Try<TelegramResource<T>> provideBy(Command command);

}

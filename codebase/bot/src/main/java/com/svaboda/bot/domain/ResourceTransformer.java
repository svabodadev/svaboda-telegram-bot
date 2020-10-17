package com.svaboda.bot.domain;

import com.svaboda.bot.commands.Command;
import io.vavr.control.Try;

public interface ResourceTransformer<T> {

    Try<T> asContent(Command command, T resource);

}

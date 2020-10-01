package com.svaboda.telegram.domain;

import com.svaboda.telegram.commands.Command;
import io.vavr.control.Try;

public interface ResourceTransformer<T> {

    Try<T> asContent(Command command, T resource);

}

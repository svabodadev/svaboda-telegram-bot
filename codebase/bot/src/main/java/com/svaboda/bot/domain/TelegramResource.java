package com.svaboda.bot.domain;

import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
public class TelegramResource<T> {

    T resource;

    public TelegramResource(T resource) {
        this.resource = checkNotNull(resource);
    }

}

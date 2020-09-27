package com.svaboda.telegram.bot;

import io.vavr.control.Try;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageProcessor {

    Try<Void> processWith(Update update, TelegramLongPollingBot bot);

}

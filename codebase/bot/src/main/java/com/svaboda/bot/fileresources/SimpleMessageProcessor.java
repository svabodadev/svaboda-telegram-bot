package com.svaboda.bot.fileresources;

import com.svaboda.bot.bot.MessageProcessor;
import com.svaboda.bot.commands.Commands;
import com.svaboda.bot.commands.Command;
import com.svaboda.bot.domain.ResourceProvider;
import com.svaboda.bot.domain.TelegramResource;
import com.svaboda.statistics.StatisticsRegistration;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.svaboda.utils.ArgsValidation.notEmpty;
import static com.svaboda.utils.ArgsValidation.notNull;

@RequiredArgsConstructor
class SimpleMessageProcessor implements MessageProcessor {

    private final ResourceProvider<String> resourceProvider;
    private final StatisticsRegistration statisticsRegistration;
    private final Commands commands;

    @Override
    public Try<Void> processWith(Update update, TelegramLongPollingBot bot) {
        return toCommand(update)
                .peek(command -> statisticsRegistration.register(command, toChatId(update)))
                .flatMap(resourceProvider::provideBy)
                .map(TelegramResource::resource)
                .map(answer -> toMessage(update, answer))
                .flatMap(message -> Try.run(() -> bot.execute(message)));
    }

    private Try<Command> toCommand(Update update) {
        return Option.of(update.getMessage())
                .toTry()
                .peek(message -> {
                    notEmpty(message.getText());
                    notNull(message.getChatId());
                })
                .map(Message::getText)
                .map(commands::byName)
                .recoverWith(failure -> Try.failure(new RuntimeException("Received not processable message", failure)));
    }

    private SendMessage toMessage(Update update, String answer) {
        return new SendMessage(toChatId(update), answer);
    }

    private Long toChatId(Update update) {
        return update.getMessage().getChatId();
    }

}

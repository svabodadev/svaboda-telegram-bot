package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.bot.MessageProcessor;
import com.svaboda.telegram.commands.Commands;
import com.svaboda.telegram.commands.Command;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.TelegramResource;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.svaboda.telegram.support.ArgsValidation.notEmpty;
import static com.svaboda.telegram.support.ArgsValidation.notNull;

@RequiredArgsConstructor
class SimpleMessageProcessor implements MessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageProcessor.class);

    private final ResourceProvider<String> resourceProvider;
    private final Commands commands;

    @Override
    public Try<Void> processWith(Update update, TelegramLongPollingBot bot) {
        return toCommand(update)
                .flatMap(resourceProvider::provideBy)
                .map(TelegramResource::resource)
                .peek(__ -> System.out.println("### resource: "+__))//todo
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
                .peek(command -> LOG.info("Resolved message for command <<{}>>", command.name()))
                .recoverWith(failure -> Try.failure(new RuntimeException("Received not processable message", failure)));
    }

    private SendMessage toMessage(Update update, String answer) {
        return new SendMessage(update.getMessage().getChatId(), answer);
    }

}

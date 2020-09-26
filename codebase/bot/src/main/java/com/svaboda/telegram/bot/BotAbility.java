package com.svaboda.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.control.Try;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.sender.SilentSender;

import java.util.function.Supplier;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

enum BotAbility {

    WELCOME("welcome", () -> "some invitation", PUBLIC, ALL, 0),
    WEB("web", () -> "https://core.telegram.org/bots/api", PUBLIC, ALL, 0),
    HELP("help", () -> "info how to get help", PUBLIC, ALL, 0),
    OPTION("option", () -> "some option", PUBLIC, ALL, 0);

    private final String command;
    private final Supplier<String> resource;
    private final Privacy privacy;
    private final Locality locality;
    private final int argsNumber;

    BotAbility(String command, Supplier<String> resource, Privacy privacy, Locality locality, int argsNumber) {
        this.command = command;
        this.resource = resource;
        this.privacy = privacy;
        this.locality = locality;
        this.argsNumber = argsNumber;
    }

    Try<Ability> asAbilityWith(SilentSender silentSender) {
        return Try.of(() ->
                Ability.builder()
                        .name(command)
                        .privacy(privacy)
                        .locality(locality)
                        .input(argsNumber)
                        .action(ctx -> silentSender.send(resource.get(), ctx.chatId()))
                        .build()
                )
                .peek(__ -> LoggingProvider.LOG.info("Resolved Ability for command <<{}>>", command))
                .onFailure(failure -> LoggingProvider.LOG.error("Error occurred on processing message", failure));
    }

    private static class LoggingProvider {
        final static Logger LOG = LoggerFactory.getLogger(LoggingProvider.class);
    }

    private static class ResourceProvider {
        //todo
    }

}

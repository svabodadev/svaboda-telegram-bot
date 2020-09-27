package com.svaboda.telegram.bot;

import com.svaboda.telegram.domain.Command;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.TelegramResource;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;

@RequiredArgsConstructor
class SimpleCommandHandler implements CommandHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleCommandHandler.class);

    private final ResourceProvider<String> resourceProvider;

    @Override
    public Try<Ability> handle(Command command, SilentSender sender) {
        System.out.println("### handle abaility");
        return resourceProvider.provideBy(command)
                .map(TelegramResource::resource)
                .peek(__ -> System.out.println("### resource: "+__))
                .map(text -> asAbility(command, sender, text))
                .peek(__ -> LOG.info("Resolved Ability for command <<{}>>", command.commandName()))
                .onFailure(failure ->
                        LOG.error("Error occurred on processing message for command <<{}>>",
                                command.commandName(),
                                failure)
                );
    }

    private Ability asAbility(Command command, SilentSender sender, String text) {
        return Ability.builder()
            .name(command.commandName())
            .privacy(command.privacy())
            .locality(command.locality())
            .input(command.argsNumber())
            .action(ctx -> sender.send("stub stub", ctx.chatId()))//todo
            .build();
    }

}

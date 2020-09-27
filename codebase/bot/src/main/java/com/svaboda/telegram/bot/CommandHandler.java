package com.svaboda.telegram.bot;

import com.svaboda.telegram.domain.Command;
import io.vavr.control.Try;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;

public interface CommandHandler {

    Try<Ability> handle(Command command, SilentSender sender);

}

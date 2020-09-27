package com.svaboda.telegram.bot;

import com.svaboda.telegram.domain.Command;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.toggle.AbilityToggle;

import java.util.Arrays;

import static com.svaboda.telegram.domain.Command.MAIN;

public class Bot extends AbilityBot {

    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    @Autowired
    private static CommandHandler commandHandler;

    private final int creatorId;

    Bot(BotProperties botProperties) {
        super(botProperties.token(), botProperties.name());
        System.out.println("### in constructor");
        this.creatorId = botProperties.creatorId();
//       addAbilities(commandHandler, abilityToggle, silent)
//            .onFailure(failure -> LOG.error("Failed on adding abilities", failure))
//            .peek(__ -> LOG.info("All abilities successfully added"))
//            .get();
    }

    @Override
    public int creatorId() {
        return creatorId;
    }

    public Ability main() {
        return commandHandler.handle(MAIN, silent)
                .get();
    }
//
//    public Ability aboutUs() {
//        return commandHandler.handle(ABOUT_US, silent)
//                .get();
//    }
//
//    public Ability inPoland() {
//        return commandHandler.handle(IN_POLAND, silent)
//                .get();
//    }
//
//    public Ability ruTest() {
//        return commandHandler.handle(RU_TEST, silent)
//                .get();
//    }
//                    LOG.info("Processing command <<{}>>", command.commandName());

//    private static Try<Void> addAbilities(CommandHandler commandHandler, AbilityToggle toggle, SilentSender sender) {
//        System.out.println("### adding abilities");
//        return Try.run(() -> Arrays.asList(Command.values())
//                .forEach(
//                        command -> {
//                            LOG.info("Processing command <<{}>>", command.commandName());
//                            var ability = commandHandler.handle(command, sender).get();
//                            toggle.processAbility(commandHandler.handle(command, sender).get());
//                            System.out.println("### is ability on? " + !toggle.isOff(ability));
//                        }
//                )
//            );
//    }

}

package com.svaboda.telegram.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import static com.svaboda.telegram.bot.BotAbility.*;

public class Bot extends AbilityBot {

    private final int creatorId;

    Bot(BotProperties botProperties) {
        super(botProperties.token(), botProperties.name());
        this.creatorId = botProperties.creatorId();
    }

    @Override
    public int creatorId() {
        return creatorId;
    }

    public Ability welcome() {
        return WELCOME.asAbilityWith(silent)
                .get();
    }

    public Ability web() {
        return WEB.asAbilityWith(silent)
                .get();
    }

    public Ability help() {
        return HELP.asAbilityWith(silent)
                .get();
    }

    public Ability option() {
        return OPTION.asAbilityWith(silent)
                .get();
    }

}

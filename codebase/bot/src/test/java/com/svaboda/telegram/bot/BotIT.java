package com.svaboda.telegram.bot;

import org.telegram.telegrambots.meta.bots.AbsSender;

public class BotIT {

    public static final int USER_ID = 1337;
    public static final long CHAT_ID = 1337L;

    private Bot bot;
    private AbsSender silent;

//    @BeforeEach
//    public void setUp(BotProperties botProperties, Responder responder) {
//        DefaultBotOptions
//        // Create your bot
//        bot = new Bot(botProperties, responder);
//        // Create a new sender as a mock
//        silent = mock(AbsSender.class);
//        // Set your bot silent sender to the mocked sender
//        // THIS is the line that prevents your bot from communicating with Telegram servers when it's running its own abilities
//        // All method calls will go through the mocked interface -> which would do nothing except logging the fact that you've called this function with the specific arguments
//        // Create setter in your bot
//        bot.(silent);
//    }
//
//    @Test
//    public void canSayHelloWorld() {
//        Update upd = new Update();
//        // Create a new User - User is a class similar to Telegram User
//        User user = new User(USER_ID, "Abbas", false, "Abou Daya", "addo37", null);
//        // This is the context that you're used to, it is the necessary conumer item for the ability
//        MessageContext context = MessageContext.newContext(upd, user, CHAT_ID);
//
//        // We consume a context in the lamda declaration, so we pass the context to the action logic
//        bot.saysHelloWorld().action().accept(context);
//
//        // We verify that the silent sender was called only ONCE and sent Hello World to CHAT_ID
//        // The silent sender here is a mock!
//        Mockito.verify(silent, times(1)).send("Hello World!", CHAT_ID);
//    }
}

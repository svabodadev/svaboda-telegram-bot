web: cd codebase/bot/ && gradle clean && gradle bootJar \
    && java $JAVA_OPTS -Dserver.port=3128 -Denv.bot.token=$BOT_TOKEN -Denv.bot.name=$BOT_NAME -jar build/libs/bot-0.0.1-SNAPSHOT.jar
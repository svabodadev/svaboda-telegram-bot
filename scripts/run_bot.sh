#!/usr/bin/env bash
cd ../codebase && gradle clean && gradle test && gradle bootJar
java -jar -Denv.telegram.bot.name="$BOT_NAME" -Denv.telegram.bot.token="$BOT_TOKEN" bot/build/libs/bot-1.0.0-SNAPSHOT.jar

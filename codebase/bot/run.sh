#!/bin/bash
exec java -Denv.telegram.bot.name="$_BOT_NAME" -Denv.telegram.bot.token="$_BOT_TOKEN" -jar /app/bot-1.0.0-SNAPSHOT.jar

#!/bin/bash
exec java -Denv.telegram.bot.name="$BOT_NAME" -Denv.telegram.bot.token="$BOT_TOKEN" -jar /app/bot-1.0.0-SNAPSHOT.jar
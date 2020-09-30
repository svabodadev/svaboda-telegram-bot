# telegram-test-bot

###Env variables (not included into application.yml and must be provided during deployment/running locally):
    - BOT_NAME
    - BOT_TOKEN

###Running locally (gradle required):
Run script - to build, test and run bot service:
```
cd scripts && chmod +x "run_bot.sh" && ./run_bot.sh
```

###Running with docker (docker required):
```
cd codebase && docker build --build-arg _name="$BOT_NAME" --build-arg _token="$BOT_TOKEN" -t bot-service .
```

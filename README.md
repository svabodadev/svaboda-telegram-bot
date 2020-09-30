# telegram-test-bot

###Env variables (not included into application.yml and must be provided during deployment/running locally):
    - BOT_NAME
    - BOT_TOKEN



###Run locally (gradle required):***

Run script - to build, test and run bot service:
```
cd scripts && chmod +x "run_bot.sh" && ./run_bot.sh
```

###Run with docker (docker required):
```
cd codebase && docker build --build-arg _name=${BOT_NAME} --build-arg _token=${BOT_TOKEN} -t bot-service .
docker run <image id produced by docker build>
```

###Deploy to Heroku cloud with container registry (required access to our [Heroku](https://dashboard.heroku.com/apps) and installed [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli)):
```
heroku login
heroku container:login
cd codebase && heroku container:push web
heroku container:release web
```



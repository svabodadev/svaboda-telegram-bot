# telegram-test-bot

## Configuration variables (not included into application.yml) - not relevant for Heroku cloud deployment
|Key|Description|Example|Mandatory|
|---|---|---|---|
|PORT|TCP port on which application will listen incoming http requests|8080|Yes
|BOT_NAME|telegram bot name|any-bot-name|Yes
|BOT_TOKEN|token to access telegram bot api|some-secret|Yes

## Run using scripts (assuming one is in the repository's root dir)
- locally (gradle required)
```
cd scripts && chmod +x "run_bot.sh" && ./run_bot.sh
```
- with Docker ([Docker](https://docs.docker.com/get-docker/) required):
```
cd codebase && docker build --build-arg _port=${PORT} --build-arg _name=${BOT_NAME} --build-arg _token=${BOT_TOKEN} -t bot-service . && cd ..
docker run bot-service:latest
```

## Deploy to Heroku cloud with container registry (required access to our [Heroku](https://dashboard.heroku.com/apps) and installed [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli)):
```
heroku login
heroku container:login
cd codebase && heroku container:push web && cd ..
heroku container:release web

```
To scale with heroku:
```
numberOfInstances=1
heroku ps:scale web=${numberOfInstances} --app svaboda-bot
```

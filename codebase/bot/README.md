# telegram-bot

## Configuration variables
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
cd codebase
docker build -f bot/Dockerfile --build-arg _port=${PORT} --build-arg _name=${BOT_NAME} --build-arg _token=${BOT_TOKEN} -t bot-service .
docker run bot-service:latest
```

## Deploy to Heroku cloud with container registry
Important: for this kind of build/deployment there is no need to set up env vars. Requires access to our [Heroku](https://dashboard.heroku.com/apps) and [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) installed):
Before any action one need to login: `heroku login`. If it's first deployment: `heroku git:remote -a <heroku-application-name-here>`
To build image:
```
heroku container:login
cd codebase/bot
heroku container:push web --context-path ..
```
To deploy: `heroku container:release web`

One can find more on the Heroku [website](https://devcenter.heroku.com/articles/container-registry-and-runtime)

To scale:
```
numberOfInstances=1
heroku ps:scale web=${numberOfInstances} --app <heroku-application-name-here>
```

Check logs:
`heroku logs --tail`

One can use [worker](https://devcenter.heroku.com/articles/background-jobs-queueing) process for better service
availability (ex. during deployments):

deploy worker to take over the work of the main web process while it is being redeployed:
build additional worker:
```
cd codebase/bot
heroku container:push worker --context-path ..
```
Deploy it: `heroku container:release worker`

next apply changes (ex. new code revision), build and deploy main web process and after that scale worker to 0:
`heroku ps:scale worker=0`

## Monitoring and alerting
[Runscope](https://www.runscope.com/radar/0mr8x407k0jr/dcb9596f-a859-472b-a8f7-304f5ae9041c/overview) is used for now. Executes diagnostic http call against GET /_ready endpoint every minute. 3 failures trigger email alert.

## TBD
    - do not keep article resources as files - implement scheduled job to read content from provided google docs links
    - replace Runscope with own service (free of charge)
    - collect metrics, stats (calls by topic counter, etc.)
    - add CICD pipeline (consider CircleCI)


# monitor service
Service to execute diagnostic http calls against other services in the system.

## Configuration variables
|Key|Description|Example|Mandatory|
|---|---|---|---|
|PORT|TCP port on which application will listen incoming http requests|8080|Yes
|INTERVAL_SEC|time interval in seconds between diagnostic calls|60|Yes
|SERVICES_BASE_URLS|coma separated list of services to monitor|some_service.com,other_service.com|Yes
|DIAGNOSTIC_ENDPOINT|common diagnostic endpoint exposed in services|_someEndpoint|Yes

## Run using scripts (assuming one is in the repository's root dir)
- locally (gradle required)
```
cd scripts && chmod +x "run_monitor.sh" && ./run_monitor.sh
```
- with Docker ([Docker](https://docs.docker.com/get-docker/) required):
```
cd codebase
docker build -f monitor/Dockerfile --build-arg _port=${PORT} --build-arg _intervalSec=${INTERVAL_SEC} \
    --build-arg _servicesBaseUrls=${SERVICES_BASE_URLS} \
    --build-arg _diagnosticEndpoint=${DIAGNOSTIC_ENDPOINT} -t monitor-service .
docker run monitor-service:latest
```

## Deploy to Heroku cloud with container registry
Important: for this kind of build/deployment there is no need to set up env vars. Requires access to our [Heroku](https://dashboard.heroku.com/apps) and [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) installed):
Before any action one need to login: `heroku login`. If it's first deployment: `heroku git:remote -a <heroku-application-name-here>`
To build image:
```
heroku container:login
cd codebase/monitor
heroku container:push web --context-path ..
```
To deploy: `heroku container:release web`

One can find more on the Heroku [website](https://devcenter.heroku.com/articles/container-registry-and-runtime)

## TBD
Add tests
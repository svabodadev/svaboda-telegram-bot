#!/usr/bin/env bash

heroku git:remote -a "$HEROKU_APP_NAME"
heroku container:login
cd ../codebase/"$SERVICE_NAME" || exit
heroku container:push "$PROCESS_TYPE" --context-path ..

#!/usr/bin/env bash

heroku git:remote -a "$HEROKU_APP_NAME"
heroku container:login
heroku container:release "$PROCESS_TYPE"

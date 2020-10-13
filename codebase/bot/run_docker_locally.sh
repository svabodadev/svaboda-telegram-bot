#!/usr/bin/env bash

cd ..
docker build -f bot/Dockerfile --build-arg _port="$PORT" --build-arg _name="$BOT_NAME" --build-arg _token="$BOT_TOKEN" -t bot-service .
docker run bot-service:latest
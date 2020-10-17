#!/usr/bin/env bash

cd ..
docker build -f monitor/Dockerfile \
    --build-arg _port="$PORT" \
    --build-arg _intervalSec="$INTERVAL_SEC" \
    --build-arg _servicesBaseUrls="$SERVICES_BASE_URLS" \
    -t monitor-service .
docker run monitor-service:latest
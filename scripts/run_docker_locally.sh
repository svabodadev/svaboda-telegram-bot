#!/usr/bin/env bash

cd ../codebase/"$SERVICE_NAME" || exit

chmod +x "run_docker_locally.sh" && ./run_docker_locally.sh

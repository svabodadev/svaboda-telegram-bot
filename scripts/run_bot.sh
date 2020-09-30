#!/usr/bin/env bash
cd ../codebase && gradle clean && gradle test && gradle bootJar
java -jar bot/build/libs/bot-1.0.0-SNAPSHOT.jar

#!/usr/bin/env bash
cd ../codebase && gradle clean && gradle test && gradle bootJar
java -jar monitor/build/libs/monitor-1.0.0-SNAPSHOT.jar

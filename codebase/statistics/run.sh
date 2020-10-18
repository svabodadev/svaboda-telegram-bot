#!/bin/bash
exec java \
    -XX:+UseContainerSupport \
    -Xmx300m \
    -Xss512k \
    -XX:CICompilerCount=2 \
    -jar /app/statistics-1.0.0-SNAPSHOT.jar
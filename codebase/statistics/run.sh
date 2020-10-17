#!/bin/bash
exec java \
    -XX:MaxRAMPercentage=80.0 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -jar /app/statistics-1.0.0-SNAPSHOT.jar
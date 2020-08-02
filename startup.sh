#!/bin/bash

### start infra docker
docker-compose up -d

### build modules
./gradlew :api:build
./gradlew :event:build
./gradlew :messagin:build
### start modules
./gradlew :api:bootRun &
./gradlew :event:bootRun &
./gradlew :messagin:bootRun &

#!/usr/bin/env bash

./gradlew clean build bintrayUpload -PbintrayUser=blundell -PbintrayKey=$1 -PdryRun=false

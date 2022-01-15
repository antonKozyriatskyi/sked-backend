#!/bin/sh

set -e

echo "Building the app"
./gradlew :app:installDist

echo "Creating docker image and pushing it to Heroku"
heroku container:push web --app app-sked

echo "Deploying image"
heroku container:release web --app app-sked

echo "Deploy finished"

$SHELL

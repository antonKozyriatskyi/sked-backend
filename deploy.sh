#!/bin/sh

set -e

echo "-- Building the app"
echo ""

./gradlew :app:installDist

echo ""
echo "-- Creating docker image and pushing it to Heroku"
echo ""

cd app
heroku container:push web --app app-sked

echo ""
echo "-- Deploying image"
echo ""
heroku container:release web --app app-sked

echo ""
echo "-- Deploy finished"

cd ..

$SHELL

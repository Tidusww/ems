#!/bin/sh
atool-build --config webpack.build.js

cd ../../../
mvn clean package -Dmaven.test.skip -Ptest


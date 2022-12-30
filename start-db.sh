#!/bin/bash

docker run --name sw-project-movie-rator -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mysql:latest

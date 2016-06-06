#!/usr/bin/env bash

reponame="notification-base"

response=`curl -s "http://sonar.precollab.schibsted.io/api/resources?resource=com.schibsted.notification:$reponame&metrics=uncovered_lines&includetrends=true"`

diff=`echo $response | grep -o '"var1":[^,]*,' | cut -d':' -f2 | cut -d'.' -f1`

if (( $diff > 0 )); then
    echo "New uncovered lines: $diff. Aborting build."
    exit 1
fi

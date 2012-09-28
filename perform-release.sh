#!/bin/sh
MAVEN_OPTS="-XX:MaxPermSize=512m -Xmx512m" mvn -X release:perform -PuiAll,netAll,renderers-all,persistence-all,jobsAll

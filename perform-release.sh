#!/bin/sh
MAVEN_OPTS="-XX:MaxPermSize=512m -Xmx512m" mvn release:perform -PuiAll,netAll,renderers-all,persistence-all,jobsAll

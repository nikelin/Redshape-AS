#!/bin/sh
MAVEN_OPTS="-XX:MaxPermSize=512m -Xmx512m" mvn --batch-mode release:clean release:prepare -PuiAll,netAll,renderers-all,persistence-all,jobsAll

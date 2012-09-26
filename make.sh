export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=512m" &&
mvn  clean install -PnetAll,renderers-all,persistence-all,uiAll,jobsAll -DskipTests=true

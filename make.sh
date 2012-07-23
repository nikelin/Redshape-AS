export M2_HOME=/usr/share/maven &&
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=512m" &&
/usr/share/maven/bin/mvn  clean install -PnetAll,renderers-all,persistence-all,uiAll,jobsAll
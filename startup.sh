if [ -z $OPENFIRE_PATH ]
then
    OPENFIRE_PATH=/opt/openfire
fi;
echo "OpenFire path: "$OPENFIRE_PATH;

if [ -z $RED5_PATH ]
then
    RED5_PATH=/opt/red5
fi;
echo "Red5 path: "$RED5_PATH;

if [ -z $AMQ_PATH ]
then
    AMQ_PATH=/opt/activemq
fi
echo "ActiveMQ path: "$AMQ_PATH;

if [ -z $VIO_PATH ]
then
    VIO_PATH=/opt/cam/build
fi;
echo "Application server path: "$VIO_PATH;

if [ -d $AMQ_PATH ]
then
    // Check that AMQ not running at the moment
    if [ -f $AMQ_PATH/data/kahadb/lock ]
    then
        echo "AMQ alredy started...";
    else
        AMQ_STARTER=$AMQ_PATH/bin/activemq;

        if [ -f $AMQ_STARTER ]
        then
            echo "Starting ActiveMQ servers bridge instance...";
            sudo sh $AMQ_STARTER;
            sleep 3;
        else
            echo "Cannot start ActiveMQ instance"
        fi
    fi;
else
    echo "AMQ not installed!";
fi

if [ -d $OPENFIRE_PATH ]
then
    OPENFIRE_STARTER=$OPENFIRE_PATH/bin/openfire;

    if [ -f $OPENFIRE_STARTER ]
    then
        echo "Starting OpenFire XMPP server instance...";
        sudo sh $OPENFIRE_STARTER start
        sleep 3;
    else
        echo "Cannot start OpenFire instance...";
    fi;
else
    echo "OpenFire not installed!";
    exit;
fi

if [ -d $RED5_PATH ]
then
    echo "Starting Red5 media server instance...";
    cd $RED5_PATH;
    sudo java -jar boot.jar
    sleep 3;
else
    echo "Red5 server not installed!";
fi;

if [ -d $VIO_PATH ]
then
    BOOTSTRAP_JAR=$VIO_PATH/bootstrap.jar;
    SCHEDULER_JAR=$VIO_PATH/scheduler.jar;
    if [ ! -f $BOOTSTRAP_JAR ]
    then
        CORRUPTED=1;
    fi;

    if [ ! -f $SCHEDULER_JAR ]
    then
        CORRUPTED=1;
    fi

    if [ -z CORRUPTED ]
    then
        echo "VIO structure seems to be corrupted!";
        echo "Exiting...";
        exit;
    fi

    echo "Starting API server instance...";

    echo "Bootstrapping server instance...";
    java -jar $BOOTSTRAP_JAR --dataPath=$VIO_PATH/resources;

    echo "Starting scheduler application...";
    java -jar $SCHEDULER_JAR;

    echo "Successfully started.";
else
    echo "VIO server root not found...";
    echo "Exiting...";
    exit;
fi;

package com.redshape.messaging;

import org.apache.activemq.ActiveMQConnection;

import javax.jms.*;
import java.net.URISyntaxException;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date Apr 5, 2010
 */
public class AMQManager extends AbstractJMSManager {

    public Connection createConnection( String uri ) throws JMSException, URISyntaxException {
        return ActiveMQConnection.makeConnection( uri );
    }

    public Connection createConnection( String user, String password, String uri ) throws JMSException, URISyntaxException {
        return ActiveMQConnection.makeConnection(user, password, uri);
    }
}

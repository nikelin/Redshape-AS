package com.vio.messaging;

import javax.jms.*;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TimerTask;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date Apr 6, 2010
 */
public interface JMSManager {
    public static Destination UNKNOW_DESTINATION = new Topic() {
        public String getTopicName() {
            return "unknown";
        }
    };

    public static Destination SCHEDULER_DESTINATION = new Queue() {
        public String getQueueName() {
            return "scheduler";
        }

        @Override
        public String toString() {
            return this.getQueueName();
        }
    };

    public final static Destination API_SERVER_DESTINATION = new Queue() {
        public String getQueueName() {
            return "api_server";
        }

        @Override
        public String toString() {
            return this.getQueueName();
        }
    };

    public void initConnection( String user, String password, String uri ) throws JMSException, URISyntaxException;

    public Connection getActiveConnection() throws JMSException;

    public Session createSession() throws JMSException;

    public Session createSession( boolean transacted, int aknowlegeMode ) throws JMSException;

    public Connection createConnection( String uri ) throws JMSException, URISyntaxException;

    public Connection createConnection( String user, String password, String uri ) throws JMSException, URISyntaxException;

    public Message createMapMessage( Session session, Destination to, Destination from, Map<String, Object> messageData )
                    throws JMSException;

    public void onMessage( Message message ) throws JMSException;

    public void addReceivingTask( TimerTask task ) throws JMSException;

    public void addMessageListener( MessageListener listener );
}

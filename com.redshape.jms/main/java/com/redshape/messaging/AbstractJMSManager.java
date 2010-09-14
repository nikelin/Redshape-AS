package com.redshape.messaging;

import org.apache.log4j.Logger;

import javax.jms.*;
import java.net.URISyntaxException;
import java.util.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date Apr 13, 2010
 */
public abstract class AbstractJMSManager implements JMSManager {
    private static final Logger log = Logger.getLogger( AbstractJMSManager.class );    

    private Connection connection;
    private MessagingHandler handler;
    private Set<MessageListener> listeners = new HashSet<MessageListener>();

    private Timer receiveTimer = new Timer("Jobs receiving timer");

    public void initConnection( String user, String password, String uri ) throws JMSException, URISyntaxException {
        this.connection = this.createConnection( user, password, uri );
        this.connection.start();
    }

    public Connection getActiveConnection() throws JMSException {
        if ( this.connection == null ) {
            throw new JMSException("Connection not initialized!");
        }

        return this.connection;
    }

    public Session createSession() throws JMSException {
        return this.createSession( true, Session.AUTO_ACKNOWLEDGE );
    }

    public Session createSession( boolean transacted, int aknowledgedMode ) throws JMSException {
        return this.getActiveConnection().createSession(transacted, aknowledgedMode);
    }

    public Message createMapMessage( Session session, Destination to, Destination from, Map<String, Object> messageData )
                    throws JMSException {
        Message message = session.createMessage();
        message.setJMSMessageID( this.generateMessageID() );
        message.setJMSDeliveryMode( DeliveryMode.NON_PERSISTENT );
        message.setJMSDestination( to );
        message.setJMSReplyTo( from );

        for ( String key : messageData.keySet() ) {
            message.setObjectProperty( key, messageData.get(key) );
        }

        return message;
    }

    protected String generateMessageID() {
        return String.valueOf( new Date().getTime() );
    }

    public void addReceivingTask( TimerTask task ) {
        receiveTimer.scheduleAtFixedRate( task, JMSManagerFactory.MESSAGES_RECEIVE_DELAY, JMSManagerFactory.MESSAGES_RECEIVE_PERIOD );
    }

    public void addMessageListener( MessageListener listener ) {
        this.listeners.add(listener);
    }

    public void onMessage( Message message ) throws JMSException {
        for ( MessageListener listener : this.listeners ) {
            listener.onMessage(message);
        }
    }

    public void cancelTasks() {
        receiveTimer.purge();
    }

    public MessagingHandler getHandler() {
        return this.handler;
    }
}

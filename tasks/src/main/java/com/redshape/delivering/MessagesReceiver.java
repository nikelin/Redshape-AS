package com.redshape.delivering;

import org.apache.log4j.Logger;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date Apr 16, 2010
 */
public class MessagesReceiver extends TimerTask {
    private static final Logger log = Logger.getLogger( MessagesReceiver.class );
    private JMSManager manager;
    private Destination to;
    private Destination from;
    private Map<String, Object> filters = new HashMap<String, Object>();

    public MessagesReceiver( Destination to, JMSManager manager ) {
        this( to, null, manager );
    }

    public MessagesReceiver( Destination to, Destination from, JMSManager manager ) {
        this.manager = manager;
        this.to = to;
        this.from = from;
    }

    @Override
    public void run() {
        try {
            Session session = manager.createSession();
            session.run();

            System.out.println("Starting consuming loop to destination " + String.valueOf( this.to ) );
            System.out.println("Filter : " + this.getFilter() );

            MessageConsumer consumer = session.createConsumer( this.to, this.getFilter()  );
            for ( ; ; ) {
                Message message = consumer.receive();
                System.out.println( "Received message: " + String.valueOf(message) );
                if ( message == null ) {
                    break;
                }

                manager.onMessage(message);
            }

            consumer.close();
            session.close();
        } catch ( JMSException e ) {
            log.error("Scheduler tasks receive error!", e);
        }
    }

    public String getFilter() {
        StringBuilder builder = new StringBuilder();
        if ( this.from != null ) {
            builder.append("JMSReplyTo='")
                   .append( this.to.toString() )
                   .append( "'" );
        }

        for ( String key : this.filters.keySet() ) {
            builder.append(";")
                   .append( key )
                   .append( "=" )
                   .append( "'" )
                   .append( this.filters.get(key) )
                   .append( "'" );
        }

        return builder.toString();
    }

    public void addFilter( String name, String value ) {
        this.filters.put( name, value );
    }
};

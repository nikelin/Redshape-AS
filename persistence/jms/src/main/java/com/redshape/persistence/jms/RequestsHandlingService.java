package com.redshape.persistence.jms;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;
import com.redshape.persistence.protocol.IQueryMarshaller;
import com.redshape.persistence.protocol.ProtocolException;
import org.apache.log4j.Logger;

import javax.jms.*;
import java.lang.IllegalStateException;

/**
 * Default DAO requests handling service
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms
 * @date 1/25/12 {3:37 PM}
 */
public class RequestsHandlingService implements IRequestHandlingService {
    
    private static final Logger log = Logger.getLogger(RequestsHandlingService.class);
    
    private String queueName;
    private QueueSession session;
    private QueueConnection connection;
    private IQueryExecutorService executionService;
    private MessageConsumer consumer;
    private IQueryBuilder builder;

    private boolean state = true;

    private IQueryMarshaller protocol;

    public RequestsHandlingService( QueueConnection connection,
                                    IQueryExecutorService service,
                                    IQueryBuilder builder,
                                    IQueryMarshaller marshaller,
                                    String queueId )
        throws DAOException {
        this.builder = builder;
        this.connection = connection;
        this.protocol = marshaller;
        this.executionService = service;
        this.queueName = queueId;
        this.checkFields();
        this.init();
    }

    @Override
    public boolean isRunning() {
        return this.state;
    }

    protected IQueryBuilder getBuilder() {
        return this.builder;
    }

    protected IQueryExecutorService getExecutionService() {
        return this.executionService;
    }

    protected String getQueueName() {
        return this.queueName;
    }

    protected QueueConnection getConnection() {
        return this.connection;
    }

    protected IQueryMarshaller getProtocol() {
        return this.protocol;
    }

    protected void init() throws DAOException {
        try {
            this.connection.start();
            this.session = this.getConnection().createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue( this.getQueueName() );
            this.consumer = session.createConsumer(queue);
            session.run();
        } catch ( JMSException e ) {
            throw new DAOException("Queue consumer creating failed", e );
        }
    }
    
    protected void checkFields() {
        if ( this.getConnection() == null ) {
            throw new IllegalStateException("<null>: connection object not provided");
        }

        if ( this.getProtocol() == null ) {
            throw new IllegalStateException("<null>: queries marshaller not provided");
        }

        if ( this.getExecutionService() == null ) {
            throw new IllegalStateException("<null>: queries execution service not provided");
        }
    }

    @Override
    public void stop() {
        this.state = false;
    }

    protected Message processRequest( Message message ) throws DAOException {
        try {
            return this.getProtocol().marshal(
                message,
                this.getExecutionService().execute(
                    this.getProtocol().unmarshalQuery( this.getBuilder() , message)
                )
            );
        } catch ( ProtocolException e ) {
            throw new DAOException("Failed to marshal/unmarshal message!", e );
        }
    }
    
    protected void sendRespond( Queue destination, Message message ) throws JMSException{
        QueueSender sender = this.getConnection()
                .createQueueSession(false, Session.CLIENT_ACKNOWLEDGE)
                .createSender(destination);
        sender.send(message);
        sender.close();
    }
    
    @Override
    public void run() {
        while ( this.isRunning() ) {
            try {
                Message message = this.consumer.receiveNoWait();
                if ( message == null ) {
                    continue;
                }
                
                Destination replyDestination = message.getJMSReplyTo();
                if ( replyDestination == null ) {
                    continue;
                }
                
                this.sendRespond( (Queue) replyDestination, this.processRequest( message) );
            } catch ( JMSException e ) {
                log.error( e.getMessage(), e );
            } catch ( DAOException e ) {
                log.error( e.getMessage(), e );
            }
        }
    }
}

package com.redshape.persistence.dao.query.executors.services;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.executors.IExecutorResult;
import com.redshape.persistence.entities.IEntity;
import com.redshape.persistence.protocol.IQueryMarshaller;
import com.redshape.persistence.protocol.ProtocolException;
import com.redshape.utils.Constants;

import javax.jms.*;
import java.lang.IllegalStateException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.executors.services
 * @date 1/25/12 {1:49 PM}
 */
public class JMSExecutorsService implements IQueryExecutorService {
    
    private QueueConnection connection;
    private QueueSession session;

    private IQueryMarshaller protocol;

    private MessageConsumer consumer;
    private MessageProducer producer;

    private Queue destinationQueueAddr;
    private Destination destination;
    private String destinationQueue;
    
    private Object sendingLock = new Object();
    
    private int timeout = Constants.TIME_SECOND * 15;

    public JMSExecutorsService( QueueConnection connection,
                                IQueryMarshaller marshaller,
                                String destinationQueue )
            throws DAOException {
        this.connection = connection;
        this.protocol = marshaller;
        this.destinationQueue = destinationQueue;
        this.checkFields();
        this.init();
    }
    
    protected int getTimeout() {
        return this.timeout;
    }

    public void setTimeout( int timeout ) {
        this.timeout = timeout;
    }
    
    protected void checkFields() {
        if ( this.connection == null ) {
            throw new IllegalStateException("<null>: JMS connection not provided");
        }
    }

    protected QueueConnection getConnection() {
        return connection;
    }

    protected IQueryMarshaller getProtocol() {
        return this.protocol;
    }

    protected String getDestinationQueue() {
        return destinationQueue;
    }

    protected void init() throws DAOException {
        try {
            this.session = this.connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
            this.session.run();
        } catch ( JMSException e ) {
            throw new DAOException("Unable to establish JMS session through provided connection");
        }

        try {
            this.destinationQueueAddr = this.session.createQueue( this.getDestinationQueue() );
            this.producer = this.session.createSender( destinationQueueAddr );
        } catch ( JMSException e ) {
            throw new DAOException( "Unable to start messages producing thread", e );
        }

        try {
            this.destination = this.session.createTemporaryQueue();
            this.consumer = this.session.createConsumer( this.destination );
        } catch ( JMSException e ) {
            throw new DAOException( "Unable to start messages consuming thread", e );
        }
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> execute(IQuery query) throws DAOException {
        try {
            synchronized (this.sendingLock) {
                Message message = this.getProtocol().marshal(this.session.createObjectMessage(), query);
                message.setJMSReplyTo( this.destination );
                this.producer.send( this.destinationQueueAddr, message );
                this.session.commit();
                
                Message respond = this.consumer.receive( this.getTimeout() );
                if ( respond == null ) {
                    throw new DAOException("Request processing failed!");
                }


                return this.getProtocol().unmarshalResult( respond );
            }
        } catch ( ProtocolException e ) {
            throw new DAOException( "Query serializing failed", e );
        } catch ( JMSException e ) {
            throw new DAOException( "Failed to proceed message send", e );
        }
    }
}

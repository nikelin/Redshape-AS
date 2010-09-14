package com.redshape.messaging;

import javax.jms.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date May 3, 2010
 */
public abstract class AbstractMessagingHandler implements MessagingHandler {
    private static Destination defaultDestination = JMSManager.UNKNOW_DESTINATION;
    private MessageListener receiver;
    private JMSManager managerContext;
    private Destination selfDestination;

    public AbstractMessagingHandler( JMSManager managerContext, MessageListener receiver ) {
        this( managerContext, receiver, defaultDestination );
    }

    public AbstractMessagingHandler( JMSManager managerContext, MessageListener receiver, Destination selfDestination ) {
        this.managerContext = managerContext;
        this.setReceiver( receiver );
        this.setSelfDestination(selfDestination);
    }

    public void setSelfDestination( Destination destination ) {
        this.selfDestination = destination;
    }

    public Destination getSelfDestination() {
        return this.selfDestination;
    }

    public JMSManager getManagerContext() {
        return this.managerContext;
    }

    public void setReceiver( MessageListener receiver ) {
        this.receiver = receiver;
        this.getManagerContext().addMessageListener(receiver);
    }

    public MessageListener getReceiver() {
        return this.receiver;
    }

    public void send( IMessageRespond message ) throws JMSException {
        Session session = this.getManagerContext().createSession();

        MessageProducer producer = session.createProducer( this.getSelfDestination() );

        producer.send( this.createRespondMessage( session, message, this.getSelfDestination() ) );

        session.commit();
        session.close();
    }

    public void respond( Message message, IMessageRespond respond ) throws JMSException {
        Session session = this.getManagerContext().createSession();

        Message respondMessage = this.createRespondMessage( session, respond, message.getJMSReplyTo() );

        respondMessage.setJMSType("response");
        respondMessage.setJMSCorrelationID( message.getJMSMessageID() );

        MessageProducer producer = session.createProducer( respondMessage.getJMSDestination() );

        producer.send( respondMessage );
        
        session.commit();
        session.close();
    }

    private Message createRespondMessage( Session session, IMessageRespond respond, Destination destination ) throws JMSException {
        Message respondMessage = this.getManagerContext().createMapMessage(
            session,
            destination,
            JMSManager.API_SERVER_DESTINATION,
            respond.getParams()
        );

        System.out.println("Resulted message: " + respondMessage.toString() );

        return respondMessage;
    }


    public void receive( Message message ) throws JMSException {
        if ( this.getReceiver() == null ) {
            throw new JMSException("Income messages handler not present");
        }

        this.getReceiver().onMessage( message );
    }

}

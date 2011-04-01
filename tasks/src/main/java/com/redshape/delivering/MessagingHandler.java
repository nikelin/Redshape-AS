package com.redshape.delivering;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date May 3, 2010
 */
public interface MessagingHandler {

    public void setSelfDestination( Destination destination );

    public Destination getSelfDestination();

    public void send( IMessageRespond respond ) throws JMSException;

    public void receive( Message message ) throws JMSException;

    public void respond( Message message, IMessageRespond respond ) throws JMSException;

    public IMessageRespond createFailResponse() throws JMSException;

}

package com.redshape.tasks;

import com.redshape.messaging.AbstractMessagingHandler;
import com.redshape.messaging.IMessageRespond;
import com.redshape.messaging.JMSManager;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date May 3, 2010
 */
public class TasksHandler extends AbstractMessagingHandler {
    public TasksHandler( JMSManager managerContext ) {
        this( managerContext, null );
    }

    public TasksHandler( JMSManager managerContext, MessageListener receiver ) {
        super( managerContext, receiver );
    }

    public void respond( Message message, IMessageRespond respond) throws JMSException {
        this.respond( message, (TaskResult) respond );
    }

    public void respond( Message message, TaskResult respond ) throws JMSException {
        Session session = this.getManagerContext().createSession();
        
        Message respondMessage = this.getManagerContext().createMapMessage(
            session,
            message.getJMSReplyTo(),
            JMSManager.API_SERVER_DESTINATION,
            respond.getParams()
        );

        respondMessage.setStringProperty("execution_status", respond.getExecutionStatus().name() );

        respondMessage.setJMSType("response");
        respondMessage.setJMSCorrelationID( message.getJMSMessageID() );

        session.commit();
        session.close();
    }

    public IMessageRespond createFailResponse() throws JMSException {
        TaskResult result = new TaskResult();
        result.setExecutionStatus( TaskExecutionStatus.FAIL );

        return result;
    }

}

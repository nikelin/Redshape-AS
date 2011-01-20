package com.redshape.utils;

import com.redshape.messaging.JMSManager;
import com.redshape.messaging.JMSManagerFactory;
import com.redshape.tasks.IPersistentTask;
import com.redshape.tasks.IStatelessTask;
import com.redshape.tasks.ITask;
import org.apache.log4j.Logger;


import javax.jms.*;
import java.util.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.utils
 * @date Apr 13, 2010
 */
public final class SchedulerUtil {
    private final static Logger log = Logger.getLogger( SchedulerUtil.class );
    private static JMSManager defaultManager;

    static {
        try {
            defaultManager = JMSManagerFactory.getDefault().getManager();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }
    }

    public static void scheduleTask( ITask[] tasks ) throws JMSException {
        Session session = getManager().createSession();

        for ( ITask task : tasks ) {
            createMessage( session, task );
        }

        session.commit();
        session.close();        
    }

    public static void scheduleTask( ITask task ) throws JMSException {
        Session session = getManager().createSession();

        createMessage( session, task );

        session.commit();
    }

    private static Message createMessage( Session session, ITask task ) throws JMSException {
        Message message = getManager().createMapMessage(
            session,
            JMSManager.SCHEDULER_DESTINATION,
            JMSManager.API_SERVER_DESTINATION,
            createDataMap(task)
        );

        message.setJMSPriority( task.getPriority().ordinal() );
        message.setJMSMessageID( task.getClass().getName().concat( String.valueOf( new Date().getTime() ) ) );

        log.info( "Message to be sent: " + message.toString() );

        return message;
    }

    public static void setManager( JMSManager manager ) {
        defaultManager = manager;
    }

    private static JMSManager getManager() {
        return defaultManager;
    }

    private static Map<String, Object> createDataMap( ITask task ) throws JMSException{
        Map<String, Object> taskData = new HashMap<String, Object>();
        taskData.put( "initiator", JMSManager.API_SERVER_DESTINATION.toString() );

        if ( task.isPersistent() ) {
            taskData.put( "task_id", ( (IPersistentTask) task ).getId() );
        }

        taskData.put( "task_type", task.getClass().getCanonicalName() );
        taskData.put( "priority", task.getPriority() == null ? task.getPriority().ordinal()
                                                               : ITask.Priority.NORMAL.ordinal() );
        taskData.put( "execution_max_delay", task.getMaxExecutionDelay() );
        taskData.put( "execution_delay", task.getExecutionDelay() );

        return taskData;
    }

    private static Map<String, Object> createDataMap( IStatelessTask task ) throws JMSException {
        Map<String, Object> data = createDataMap( (ITask) task);

        for ( String propertyName : task.getProperties().keySet() ) {
            data.put( propertyName, task.getProperty(propertyName) );
        }

        return data;
    }

}

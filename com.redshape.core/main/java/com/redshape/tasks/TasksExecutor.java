package com.redshape.tasks;

import com.redshape.config.ConfigException;
import com.redshape.messaging.JMSManager;
import com.redshape.messaging.IMessageRespond;
import com.redshape.messaging.MessagingHandler;
import com.redshape.persistence.managers.ManagersFactory;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;
import java.util.Enumeration;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date Apr 13, 2010
 */
public class TasksExecutor implements MessageListener {
    private static final Logger log = Logger.getLogger( TasksExecutor.class );
    private MessagingHandler handlerContext;

    public TasksExecutor( MessagingHandler handlerContext ) {
        this.handlerContext = handlerContext;
    }

    public MessagingHandler getHandlerContext() {
        return this.handlerContext;
    }

    @Override
    public void onMessage( Message message ) {
        try {
            if ( !this.isValid(message) ) {
                return;
            }
            
            log.info("Starting scheduler job processing: " + new Date().toLocaleString() );
            
            ITask taskInstance = null;
            try {
                log.info("Starting processing task.");
                log.info("ITask type: " + message.getStringProperty("task_type") );
                if ( message.getObjectProperty("task_id") != null ) {
                    log.info("ITask ID: " + message.getIntProperty("task_id") );
                }

                Class<? extends ITask> taskClass = (Class<? extends ITask>) Class.forName( message.getStringProperty("task_type") );

                IMessageRespond result;
                try {
                    if ( IPersistentTask.class.isAssignableFrom( taskClass ) ) {
                       result = this.executePersistentTask( (Class<? extends IPersistentTask>) taskClass, message );
                    } else {
                       result = this.executeTask( (Class<? extends IStatelessTask>) taskClass, message );
                    }
                } catch ( ExecutionException e ) {
                    result = this.getHandlerContext().createFailResponse();
                }

                log.info("ITask has been successfully processed: " + new Date().toLocaleString() );

                if ( message.getBooleanProperty("respondable") ) {
                    this.getHandlerContext().respond( message, result);
                }
            } catch ( Throwable e ) {
                log.error("Command execution failed!", e);
            }

            if ( taskInstance != null && taskInstance.isPersistent() ) {  
                this.validateTask( (IPersistentTask) taskInstance);
            }
        } catch ( Throwable e ) {
            log.error("Unexpected exception");
        }
    }

    protected TaskResult executePersistentTask( Class<? extends IPersistentTask> taskClass, Message message ) throws ExecutionException {
        IPersistentTask taskInstance = null;
        try {
            taskInstance = (IPersistentTask) ManagersFactory.getDefault().getForEntity( IPersistentTask.class ).find( message.getIntProperty("task_id") );
            taskInstance.setLastExecutionTime( new Date().getTime() );

            if ( new Date().getTime() - taskInstance.getLastExecutionTime() < Integer.valueOf( Registry.getConfig().get("tasks").get("afterFailDelay").value() ) ) {
               log.info("ITask after fail delay not elapse... Ingoring.");
               throw new ExecutionException();
            }

            if ( taskInstance.isCompleted() ) {
                taskInstance.remove();
            }

            if ( !this.validateTask( taskInstance ) ) {
                throw new ExecutionException();
            }

            return taskInstance.execute();            
        } catch ( ExecutionException e ) {
            throw e;  
        } catch ( Throwable e ) {
            if ( taskInstance != null ) {
                taskInstance.markFail();
            }

            throw new ExecutionException();
        }
    }

    protected TaskResult executeTask( Class<? extends IStatelessTask> taskClass, Message message ) throws JMSException {
        try {
            IStatelessTask taskInstance = taskClass.newInstance();

            Enumeration<String> propNames = message.getPropertyNames();
            while( propNames.hasMoreElements() ) {
                String property = propNames.nextElement();
                taskInstance.setProperty( property, message.getObjectProperty(property) );
            }

            return taskInstance.execute();
        } catch ( Throwable e ) {
            throw new JMSException("Temporary task execution failed...");
        }
    }

    private boolean validateTask( IPersistentTask task ) throws ExecutionException, ConfigException {
        if ( task.getExecutionFails() > Integer.valueOf( Registry.getConfig().get("tasks").get("failsLimit").value() ) ) {
            try {
                task.remove();
                return false;
            } catch ( Throwable e ) {
                log.error("Cannot remove task from queue!");
                throw new ExecutionException();
            }
        } else {
            try {
                task.save();
                return true;
            } catch ( Throwable e ) {
                log.error("ITask state updating error!");
                throw new ExecutionException();
            }
        }
    }

    protected boolean isValid( Message message ) throws JMSException {
        return !message.getJMSReplyTo().equals(JMSManager.SCHEDULER_DESTINATION);
    }

}

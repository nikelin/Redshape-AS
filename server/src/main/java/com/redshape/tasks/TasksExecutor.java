package com.redshape.tasks;

import com.redshape.config.ConfigException;
import com.redshape.config.IConfig;
import com.redshape.delivering.IMessageRespond;
import com.redshape.delivering.JMSManager;
import com.redshape.delivering.MessagingHandler;
import com.redshape.persistence.entities.IPersistentTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    @Autowired( required = true )
    private IConfig config;
    
    public TasksExecutor( MessagingHandler handlerContext ) {
        this.handlerContext = handlerContext;
    }
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    protected IConfig getConfig() {
    	return this.config;
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

    // @FIXME: due to DAO refactoring
    protected TaskResult executePersistentTask( Class<? extends IPersistentTask> taskClass, Message message ) throws ExecutionException {
        return null;
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

    // @FIXME: due to DAO refactoring
    private boolean validateTask( IPersistentTask task ) throws ExecutionException, ConfigException {
        return false;
    }

    protected boolean isValid( Message message ) throws JMSException {
        return !message.getJMSReplyTo().equals(JMSManager.SCHEDULER_DESTINATION);
    }

}

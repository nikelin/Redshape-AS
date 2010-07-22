package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.Action;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.config.IServerConfig;
import com.vio.messaging.JMSManager;
import com.vio.messaging.JMSManagerFactory;
import com.vio.tasks.TasksExecutor;
import com.vio.tasks.TasksHandler;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:48:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessagingInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( MessagingInit.class );

    public MessagingInit() {
        this.setId( Action.MESSAGING_ID);
    }

    public void process() throws BootstrapException {
        try {
            log.info("Initializing scheduler...");
            IServerConfig config = Registry.getServerConfig();

            log.info("Initializing JMS engine...");
            JMSManagerFactory.getDefault()
                .getManager( config.getJMSAdapter() )
                .initConnection(
                    config.getJMSUser(),
                    config.getJMSPassword(),
                    config.getJMSUri()
                );

            JMSManager manager = JMSManagerFactory.getDefault().getManager();

            TasksHandler handler = new TasksHandler(manager);
            handler.setReceiver( new TasksExecutor(handler) );

            log.info("Messaging engine initialized ( on " + config.getJMSUri() + ")...");
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException();
        }
    }

}

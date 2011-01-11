package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.config.IConfig;
import com.redshape.messaging.JMSManager;
import com.redshape.messaging.JMSManagerFactory;
import com.redshape.tasks.TasksExecutor;
import com.redshape.tasks.TasksHandler;
import com.redshape.utils.Registry;
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
            IConfig config = Registry.getConfig().get("settings").get("jms");

            log.info("Initializing JMS engine...");
            JMSManagerFactory.getDefault()
                .getManager( config.get("adapter").value() )
                .initConnection(
                    config.get("user").value(),
                    config.get("password").value(),
                    config.get("uri").value()
                );

            JMSManager manager = JMSManagerFactory.getDefault().getManager();

            TasksHandler handler = new TasksHandler(manager);
            handler.setReceiver( new TasksExecutor(handler) );

            log.info("Messaging engine initialized ( on " + config.get("uri").value() + ")...");
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException();
        }
    }

}

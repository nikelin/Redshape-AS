package com.vio.scheduler;

import com.vio.applications.Application;
import com.vio.applications.ApplicationException;
import com.vio.applications.bootstrap.Action;
import com.vio.applications.bootstrap.Bootstrap;
import com.vio.applications.bootstrap.IBootstrap;
import com.vio.applications.bootstrap.actions.MessagingInit;
import com.vio.messaging.JMSManager;
import com.vio.messaging.JMSManagerFactory;
import com.vio.scheduler.listeners.JobsListener;
import com.vio.utils.Constants;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Date;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.scheduler
 * @date Apr 14, 2010
 */
public final class Main extends Application {
    private final static Logger log = Logger.getLogger( Main.class );
    private final static Integer DEFAULT_EXECUTION_DELAY = Constants.TIME_MINUTE * 2;

    public Main( String[] args, IBootstrap bootstrap ) throws ApplicationException {
        super( Main.class, args, bootstrap );

        JMSManagerFactory.MESSAGES_RECEIVE_PERIOD = Constants.TIME_SECOND * 30;
        JMSManagerFactory.DEFAULT_MANAGER_DESTINATION = JMSManager.SCHEDULER_DESTINATION;

        this.getBootstrap().disableAction( Action.SERVERS_ID );
        this.getBootstrap().disableAction( Action.DATABASE_ID );
        this.getBootstrap().disableAction(Action.PLUGINS_ID );
        this.getBootstrap().disableAction(Action.API_ID );

        this.setPidCheckup(false);
    }

    public static void main( String[] args ) {
        try {
            IBootstrap boot = new Bootstrap();
            boot.clearActionPackages();
            boot.clearActions();
            boot.addAction( new MessagingInit() );

            Main app = new Main(args,  boot);
            app.start();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            log.error("Cannot start scheduler application!");
        }
    }

    public void start() throws ApplicationException {
        try {
            super.start();
            
            JMSManagerFactory.getDefault().getManager().addMessageListener( new JobsListener(this) );

            this.getTasksScheduler().start();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ApplicationException();
        }
    }

    public Scheduler getTasksScheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }

    public Trigger createTrigger( Message msg ) throws JMSException {
        Integer maxDelay;
        try {
            maxDelay = msg.getIntProperty("execution_max_delay");
        } catch ( Throwable e ) {
            maxDelay = DEFAULT_EXECUTION_DELAY;
        }

        Integer delay;
        try {
            delay = msg.getIntProperty("execution_delay");
        } catch ( Throwable e ) {
            delay = 0;
        }

        Trigger trigger = new SimpleTrigger( String.valueOf( new Date().getTime() ), "API_ID Jobs");
        trigger.setStartTime( new Date(  new Date().getTime() + delay ) );
        trigger.setEndTime( new Date( new Date().getTime() + Math.max( delay, maxDelay ) ) );

        return trigger;
    }
}

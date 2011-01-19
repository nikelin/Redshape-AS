package com.redshape.scheduler;

import com.redshape.applications.AbstractApplication;
import com.redshape.applications.ApplicationException;
import com.redshape.applications.SpringApplication;
import com.redshape.applications.bootstrap.Action;
import com.redshape.applications.bootstrap.Bootstrap;
import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.applications.bootstrap.actions.MessagingInit;
import com.redshape.messaging.JMSManager;
import com.redshape.messaging.JMSManagerFactory;
import com.redshape.scheduler.listeners.JobsListener;
import com.redshape.utils.Constants;
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
public final class Main extends SpringApplication {
    private final static Logger log = Logger.getLogger( Main.class );
    private final static Integer DEFAULT_EXECUTION_DELAY = Constants.TIME_MINUTE * 2;

    public Main( String[] args ) throws ApplicationException {
        super(args);

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
            Main app = new Main(args);
            app.start();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            log.error("Cannot start scheduler application!");
        }
    }

    @Override
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

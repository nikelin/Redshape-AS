package com.redshape.scheduler;

import com.redshape.applications.ApplicationException;

import com.redshape.applications.SpringApplication;

import com.redshape.delivering.JMSManager;
import com.redshape.delivering.JMSManagerFactory;
import com.redshape.scheduler.listeners.JobsListener;
import com.redshape.utils.Constants;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;

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

    public void start() throws ApplicationException {
        super.start();

        try {
            JMSManagerFactory.getDefault().getManager().addMessageListener( new JobsListener(this) );

            this.getTasksScheduler().start();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new RuntimeException();
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

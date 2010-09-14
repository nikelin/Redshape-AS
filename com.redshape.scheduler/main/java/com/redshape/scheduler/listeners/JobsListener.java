package com.redshape.scheduler.listeners;

import com.redshape.scheduler.Main;
import com.redshape.scheduler.tasks.SchedulerJob;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.scheduler.listeners
 * @date Apr 16, 2010
 */
public class JobsListener implements MessageListener {
    private static final Logger log = Logger.getLogger( JobsListener.class );
    private Main application;

    public JobsListener( Main app ) {
        this.application = app;
    }

    public void onMessage(Message message ) {
        if ( message  == null ) {
            log.info("Null message...");
            return;
        }

        try {
            Scheduler scheduler = this.application.getTasksScheduler();
            
            log.info("Message recieved from: " + message.toString() );

            JobDetail detail = new JobDetail( String.valueOf( new Date().getTime() ), Scheduler.DEFAULT_GROUP, SchedulerJob.class );
            detail.getJobDataMap().put("task_id", message.getObjectProperty("task_id") );
            detail.getJobDataMap().put("task_type", message.getStringProperty("task_type") );
            detail.getJobDataMap().put("timestamp", message.getJMSTimestamp() );
            detail.getJobDataMap().put("initiator", message.getJMSReplyTo() );
            detail.getJobDataMap().put("priority", message.getJMSPriority() );

            scheduler.scheduleJob( detail, this.application.createTrigger( message ) );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }
    }

}

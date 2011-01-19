package com.redshape.scheduler.tasks;

import com.redshape.messaging.JMSManager;
import com.redshape.messaging.JMSManagerFactory;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.scheduler.tasks
 * @date Apr 13, 2010
 */
public class SchedulerJob implements Job {
    private static final Logger log = Logger.getLogger( SchedulerJob.class );

    public void execute( JobExecutionContext context ) throws JobExecutionException {
        try {
            log.info("Execution...");
            final JobDataMap jobData = context.getJobDetail().getJobDataMap();
            
            JMSManager manager = JMSManagerFactory.getDefault().getManager();

            Connection conn = manager.getActiveConnection();
            Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination initiatorDestination = (Destination) jobData.get("initiator");

            Map<String, Object> messageData = new HashMap<String, Object>();
            if ( jobData.get("task_id") != null ) {
                messageData.put( "task_id",  Integer.parseInt( jobData.get("task_id").toString() ) );
            }
            messageData.put( "task_type", jobData.get("task_type").toString() );

            log.info( initiatorDestination.toString() );
            MessageProducer producer = session.createProducer( initiatorDestination );
            producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );

            Message msg = manager.createMapMessage(session, initiatorDestination, JMSManager.SCHEDULER_DESTINATION, messageData );

            msg.setJMSPriority( jobData.getInt("priority") );
            msg.setJMSTimestamp( new Date().getTime() );

            log.info("Message to be sent: " + msg.toString() );

            producer.send(msg);
            session.commit();

            producer.close();
            session.close();
        } catch ( Throwable e ) {
            log.error( "Job execution exception", e );
            throw new JobExecutionException();
        }
    }

}

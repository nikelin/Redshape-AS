package com.redshape.notifications.transport.email;

import com.redshape.notifications.INotification;
import com.redshape.notifications.transport.*;
import com.redshape.notifications.transport.annotations.Transporter;
import com.redshape.notifications.transport.email.configuration.EmailTransportConfiguration;
import com.redshape.notifications.transport.email.destinations.EmailDestination;
import org.apache.log4j.Logger;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:06:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Transporter( name = "email" )
public class EmailTransport extends AbstractTransport<EmailTransportConfiguration, EmailDestination> {
    private static final Logger log = Logger.getLogger( EmailTransport.class );


    public void send( INotification notification, EmailDestination from, EmailDestination to ) throws TransportException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", this.getConfiguration().getHost() );
            props.put("mail.smtp.port", this.getConfiguration().getPort() );
            props.put("mail.smtp.user", this.getConfiguration().getUser() );
            props.setProperty("mail.from", from.getAddress() );

            Session session = Session.getInstance(props, null);

            MimeMessage msg = new MimeMessage( session );
            msg.setRecipients( javax.mail.Message.RecipientType.TO, to.getAddress() );
            msg.setSubject( notification.getSubject() );
            msg.setSentDate( new Date() );
            msg.setText( notification.getBody() );
            
            msg.saveChanges();

            Transport tr = session.getTransport("smtp");
            tr.connect(
                    this.getConfiguration().getHost(),
                    this.getConfiguration().getPort(),
                    this.getConfiguration().getUser(),
                    this.getConfiguration().getCredentials() );
            
            tr.sendMessage(msg, msg.getAllRecipients());

            tr.close();
        } catch ( Throwable e ) {
            throw new TransportException();
        }
    }

    public EmailTransportConfiguration createConfig() {
        return new EmailTransportConfiguration();
    }

    public EmailDestination createDestination() {
        return new EmailDestination();
    }

}

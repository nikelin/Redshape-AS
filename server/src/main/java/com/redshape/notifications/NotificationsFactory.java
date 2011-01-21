package com.redshape.notifications;

import com.redshape.notifications.annotations.Notification;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 2:57:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationsFactory implements INotificationsFactory {
    private static final Logger log = Logger.getLogger( NotificationsFactory.class );
    private static INotificationsFactory defaultInstance = new NotificationsFactory();
    private Map<String, Class<? extends INotification> > instances = new HashMap();

    public static INotificationsFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( INotificationsFactory instance ) {
        defaultInstance = instance;
    }

    public <T extends INotification> T createNotification( String id ) throws InstantiationException {
        return this.createNotification( (Class<T>) this.instances.get(id) );
    }

    public <T extends INotification> T createNotification( Class<T> clazz ) throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public void registerNotification( Class<? extends INotification> notification ) throws InstantiationException {
        Notification notificationAnnotation = notification.getClass().getAnnotation( Notification.class );
        if ( notificationAnnotation == null ) {
            throw new InstantiationException("Transport does not provides ID annotation.");
        }

        this.registerNotification( notification, notificationAnnotation.name() );
    }

    public void registerNotification( Class<? extends INotification> notification, String name ) {
        this.instances.put( name, notification );
    }

}

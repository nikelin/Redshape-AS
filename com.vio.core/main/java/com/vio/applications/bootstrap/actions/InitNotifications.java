package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.notifications.INotification;
import com.vio.notifications.NotificationsFactory;
import com.vio.notifications.annotations.Notification;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.Registry;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 6:44:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitNotifications extends AbstractBootstrapAction {

    @Override
    public void process() throws BootstrapException {
        try {
            for ( String pkg : Registry.getApiServerConfig().getNotificationsPackages() ) {
                Class<INotification>[] classes = Registry.getPackagesLoader()
                                                         .getClasses(
                                                            pkg,
                                                            new InterfacesFilter(
                                                                new Class[] { INotification.class },
                                                                new Class[] { Notification.class }
                                                            ) ) ;
                for ( Class<? extends INotification> notificationClass : classes ) {
                    NotificationsFactory.getDefault()
                                        .registerNotification( notificationClass, notificationClass.getAnnotation( Notification.class).name() );
                }
            }
        } catch ( Throwable e ) {
            throw new BootstrapException();
        }
    }

}

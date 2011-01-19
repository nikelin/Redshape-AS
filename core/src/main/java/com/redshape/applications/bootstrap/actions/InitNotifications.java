package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.config.IConfig;
import com.redshape.notifications.INotification;
import com.redshape.notifications.NotificationsFactory;
import com.redshape.notifications.annotations.Notification;
import com.redshape.utils.InterfacesFilter;

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
            for ( IConfig packageNode : this.getConfig().get("notifications").childs() ) {
                Class<INotification>[] classes = this.getPackagesLoader()
                                                         .getClasses(
                                                            packageNode.value(),
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

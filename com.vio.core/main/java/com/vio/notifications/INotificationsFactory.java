package com.vio.notifications;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 5:24:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface INotificationsFactory {

    public <T extends INotification> T createNotification( String id ) throws InstantiationException;

    public <T extends INotification> T createNotification( Class<T> notification ) throws InstantiationException;

    public void registerNotification( Class<? extends INotification> notification ) throws InstantiationException;

    public void registerNotification( Class<? extends INotification> notification, String id ) throws InstantiationException;

}

package com.redshape.notifications;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 11, 2010
 * Time: 7:48:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface INotification {

    public void addFlag( NotificationFlag flag );

    public void removeFlag( NotificationFlag flag );

    public boolean hasFlag( NotificationFlag flag );

    public String getSubject();
    
    public String getBody();

    public void setAttribute( String name, String value );

    public String getAttribute( String name );

}

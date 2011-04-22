package com.redshape.ui.notifications;

/**
 * @author nikelin
 * @date 15/04/11
 * @package com.redshape.ui.notifications
 */
public interface INotificationsManager {

	public void warn( String message );

	public void warn( String message, NotificationType type );

	public void info( String message );

	public void info( String message, NotificationType type );

	public void error( String message );

	public void error( String message, NotificationType type );

}

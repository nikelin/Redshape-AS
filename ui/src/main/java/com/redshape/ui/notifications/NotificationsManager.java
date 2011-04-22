package com.redshape.ui.notifications;

import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.windows.IWindowsManager;
import com.redshape.ui.windows.swing.ISwingWindowsManager;

import javax.swing.*;

/**
 * @author nikelin
 * @date 15/04/11
 * @package com.redshape.ui.notifications
 */
public class NotificationsManager implements INotificationsManager {

	protected enum Level {
		WARN,
		INFO,
		ERROR
	}

	protected void showNotify( String message, Level level, NotificationType type ) {
		switch ( type ) {
			case POPUP:
				this.showPopupNotify(message, level);
			break;
			case MESSAGE:
				this.showMessageNotify(message, level);
			break;
			default:
		}
	}

	protected void showMessageNotify( String message, Level level ) {
		throw new UnsupportedOperationException("Not implemented yet...");
	}

	protected void showPopupNotify( String message, Level level ) {
		int messageType;
		String messageSubject;
		switch ( level ) {
			case ERROR:
				messageSubject = "Error!";
				messageType = JOptionPane.ERROR_MESSAGE;
			break;
			case INFO:
				messageSubject = "Message";
				messageType = JOptionPane.INFORMATION_MESSAGE;
			break;
			case WARN:
				messageSubject = "Warning";
				messageType = JOptionPane.WARNING_MESSAGE;
			break;
			default:
				messageSubject = "Message";
				messageType = JOptionPane.INFORMATION_MESSAGE;
		}

		JOptionPane.showMessageDialog(
			UIRegistry.<JFrame>getRootContext(),
			message,
			messageSubject,
			messageType
		);
	}

	@Override
	public void warn(String message) {
		this.warn(message, NotificationType.POPUP );
	}

	@Override
	public void warn(String message, NotificationType type) {
		this.showNotify( message, Level.WARN, type );
	}

	@Override
	public void info(String message) {
		this.info(message, NotificationType.POPUP );
	}

	@Override
	public void info(String message, NotificationType type) {
		this.showNotify( message, Level.INFO, type );
	}

	@Override
	public void error(String message) {
		this.error(message, NotificationType.POPUP);
	}

	@Override
	public void error(String message, NotificationType type) {
		this.showNotify( message, Level.ERROR, type );
	}
}

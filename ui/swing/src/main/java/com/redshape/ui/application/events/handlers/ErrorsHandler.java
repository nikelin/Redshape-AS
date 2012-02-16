package com.redshape.ui.application.events.handlers;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.IApplication;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.StringUtils;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 23.05.11
 * Time: 2:13
 * To change this template use File | Settings | File Templates.
 */
public class ErrorsHandler implements IEventHandler {
	private static final long serialVersionUID = 6910003103871741214L;
	
	@SuppressWarnings("unused")
	private IApplication context;

    public ErrorsHandler( IApplication context ) {
        this.context = context;
    }

    @Override
    public void handle(AppEvent event) {
        Object errorDescription = event.getArg(0);
        if ( errorDescription instanceof Throwable ) {
            Throwable exception = event.getArg(0);

            int option = JOptionPane.showConfirmDialog(
                    UIRegistry.<JFrame>get(UIConstants.System.WINDOW),
                    "Some internal exception throwed. See details?",
                    "Error!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.ERROR_MESSAGE);

            if ( JOptionPane.YES_OPTION == option ) {
                UIRegistry.getNotificationsManager().error(
                        StringUtils.stackTraceAsString(exception) );
            } else if ( JOptionPane.CANCEL_OPTION == option) {
                Dispatcher.get().forwardEvent( UIEvents.Core.Exit );
            }
        } else if ( errorDescription instanceof String ){
            UIRegistry.getNotificationsManager().error( String.valueOf( event.getArg(0) ) );
        } else {
            JOptionPane.showMessageDialog(
                    UIRegistry.<JFrame>get(UIConstants.System.WINDOW),
                errorDescription != null ?
                        errorDescription.toString()
                        : "Unknow exception",
                "Error!",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

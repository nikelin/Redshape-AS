package com.redshape.ui.gwt.helpers.notification;

/**
 * Created with IntelliJ IDEA.
 * User: max.gura
 * Date: 23.08.12
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public interface INotificationFacade {

    public void info(String message);

    public void error(String message);

    public void error(Throwable caught);

    public void error(Throwable caught, String message);

    public void alert(String message);

}
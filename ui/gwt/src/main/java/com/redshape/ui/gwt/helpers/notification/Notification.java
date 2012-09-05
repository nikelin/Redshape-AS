package com.redshape.ui.gwt.helpers.notification;

/**
 * Created with IntelliJ IDEA.
 * User: nakham
 * Date: 30.08.12
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class Notification {

    public enum Type {
        ERROR,
        INFO,
        WARNING
    }

    private Type type;
    private String message;
    private Throwable exception;

    public Notification(Type type, String message, Throwable exception) {
        this.type = type;
        this.message = message;
        this.exception = exception;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }

    public static Notification createError( String message, Throwable error ) {
        return new Notification(Type.ERROR, message, error);
    }

    public static Notification createInfo( String message ) {
        return new Notification(Type.ERROR, message, null);
    }

    public static Notification createAlert( String message ) {
        return new Notification(Type.WARNING, message, null);
    }
}

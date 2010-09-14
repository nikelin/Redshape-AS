package com.redshape.notifications.transport.destination;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 5:45:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class WrongDestinationException extends Exception {

    public WrongDestinationException() {
        super();
    }

    public WrongDestinationException( String message ) {
        super(message);
    }

}

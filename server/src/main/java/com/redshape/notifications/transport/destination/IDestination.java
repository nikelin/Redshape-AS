package com.redshape.notifications.transport.destination;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 2:55:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDestination {

    public IDestination setValue( Object value ) throws WrongDestinationException;

    public Object getValue();

}

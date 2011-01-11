package com.redshape.notifications.transport.email.destinations;

import com.redshape.notifications.transport.destination.IDestination;
import com.redshape.notifications.transport.destination.WrongDestinationException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:21:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailDestination implements IDestination {
    private String address;

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public EmailDestination setValue( Object value ) throws WrongDestinationException {
        String address = String.valueOf(value);
        if ( !this.isEmail(address) ) {
            throw new WrongDestinationException();
        }

        this.setAddress( String.valueOf(value) );

        return this;
    }

    public Object getValue() {
        return this.address;
    }

    protected boolean isEmail( String value ) {
        return true;
    }
}

package com.redshape.payments.entities;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 4:32:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class PayingException extends Exception {

    public PayingException() {
        super();
    }

    public PayingException( String message ) {
        super(message);
    }

}

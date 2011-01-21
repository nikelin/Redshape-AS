package com.redshape.io;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkInteractionException extends IOException {

    public NetworkInteractionException() {
        super();
    }

    public NetworkInteractionException( String message ) {
        super( message );
    }

}

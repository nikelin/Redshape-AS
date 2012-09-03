package com.redshape.net.connection;

/**
 * @author nikelin
 * @date 12:56
 */
public class ConnectionException extends Exception {

    public ConnectionException() {
        this(null);
    }

    public ConnectionException(String message) {
        this(message, null);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

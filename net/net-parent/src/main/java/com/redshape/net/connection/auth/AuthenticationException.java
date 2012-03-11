package com.redshape.net.connection.auth;

/**
 * @author nikelin
 * @date 13:12
 */
public class AuthenticationException extends Exception {

    public AuthenticationException() {
        this(null);
    }

    public AuthenticationException(String message) {
        this(message, null);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

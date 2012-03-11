package com.redshape.net.jmx;

/**
 * @author nikelin
 * @date 20:18
 */
public class JMXException extends Exception {

    public JMXException() {
        this(null);
    }

    public JMXException(String message) {
        this(message, null);
    }

    public JMXException(String message, Throwable cause) {
        super(message, cause);
    }
}

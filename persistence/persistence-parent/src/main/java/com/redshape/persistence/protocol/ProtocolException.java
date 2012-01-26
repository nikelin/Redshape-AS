package com.redshape.persistence.protocol;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.query.protocol
 * @date 1/25/12 {3:29 PM}
 */
public class ProtocolException extends Exception {

    public ProtocolException() {
        this(null);
    }

    public ProtocolException(String message) {
        this(message, null);
    }

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}

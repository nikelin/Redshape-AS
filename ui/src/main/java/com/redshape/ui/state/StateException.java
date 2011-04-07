package com.redshape.ui.state;

import com.redshape.ui.UIException;

/**
 * States manager specific exceptions type
 * @author root
 * @date 07/04/11
 * @package com.redshape.ui.state
 */
public class StateException extends UIException {

    public StateException() {
        this(null);
    }

    public StateException( String message ) {
        this(message, null);
    }

    public StateException( String message, Throwable e ) {
        super(message, e);
    }

}

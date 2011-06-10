package com.redshape.ui.data.state;

import com.redshape.ui.application.UIException;

/**
 * States manager specific exceptions type
 * @author root
 * @date 07/04/11
 * @package com.redshape.ui.state
 */
public class StateException extends UIException {
	private static final long serialVersionUID = -8197639663235478395L;

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

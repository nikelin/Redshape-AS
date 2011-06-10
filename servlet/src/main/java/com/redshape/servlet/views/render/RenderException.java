package com.redshape.servlet.views.render;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 1:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class RenderException extends Exception {
	private static final long serialVersionUID = -5345627128392178068L;

	public RenderException() {
        super();
    }

    public RenderException( String message ) {
        super(message);
    }

}

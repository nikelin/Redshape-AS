package com.redshape.acl;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:37:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AclException extends Exception {
	private static final long serialVersionUID = -6367258819272631108L;

	public AclException() {
        super();
    }

    public AclException( String message ) {
        super(message);
    }

}

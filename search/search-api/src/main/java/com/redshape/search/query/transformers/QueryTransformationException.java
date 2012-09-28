package com.redshape.search.query.transformers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 1:37:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryTransformationException extends Exception {

    public QueryTransformationException() {
        super();
    }

    public QueryTransformationException( String message ) {
        super(message);
    }

	public QueryTransformationException( String message, Throwable e ) {
		super(message, e);
	}

}

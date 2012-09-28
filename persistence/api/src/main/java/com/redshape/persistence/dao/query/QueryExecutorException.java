package com.redshape.persistence.dao.query;

public class QueryExecutorException extends Exception {
	private static final long serialVersionUID = 8043853567769926604L;

	public QueryExecutorException() {
        this(null);
    }

    public QueryExecutorException(String message) {
        this(message, null);
    }
    
    public QueryExecutorException( String message, Throwable e ) {
    	super(message, e);
    }

}


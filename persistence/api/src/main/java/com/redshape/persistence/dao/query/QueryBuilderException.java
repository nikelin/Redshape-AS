package com.redshape.persistence.dao.query;

public class QueryBuilderException extends Exception {
	private static final long serialVersionUID = 4746193699343835136L;

	public QueryBuilderException() {
        super();
    }

    public QueryBuilderException(String message) {
        super(message);
    }

    public QueryBuilderException( String message, Throwable e ) {
        super( message, e );
    }

}



package com.redshape.persistence.dao.query;

public class QueryBuilderException extends Exception {

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



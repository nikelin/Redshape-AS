package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.QueryExecutorException;

public interface IQueryExecutor<T> {

    public T execute() throws QueryExecutorException;

}

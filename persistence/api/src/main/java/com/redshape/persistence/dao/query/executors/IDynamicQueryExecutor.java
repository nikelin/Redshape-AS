package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.statements.IArrayStatement;

public interface IDynamicQueryExecutor<T, A> extends IQueryExecutor<T> {

    public A processStatement(IArrayStatement statement) throws QueryExecutorException;

}


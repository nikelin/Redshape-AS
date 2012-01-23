package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.statements.IArrayStatement;

@SuppressWarnings("hiding")
public interface IStaticQueryExecutor<Boolean> extends IQueryExecutor<Boolean> {

    public Comparable[] processStatement(IArrayStatement statement) throws QueryExecutorException;

}


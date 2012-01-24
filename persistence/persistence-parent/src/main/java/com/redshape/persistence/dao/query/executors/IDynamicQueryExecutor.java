package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.expressions.LikeExpression;
import com.redshape.persistence.dao.query.statements.ArrayStatement;

import javax.persistence.criteria.CompoundSelection;

public interface IDynamicQueryExecutor<T> extends IQueryExecutor<T> {

    public CompoundSelection<?> processStatement(ArrayStatement statement) throws QueryExecutorException;

}


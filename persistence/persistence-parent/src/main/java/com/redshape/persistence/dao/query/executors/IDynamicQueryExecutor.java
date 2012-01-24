package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.expressions.LikeExpression;
import com.redshape.persistence.dao.query.statements.ArrayStatement;

import javax.persistence.criteria.CompoundSelection;

public interface IDynamicQueryExecutor<T, A> extends IQueryExecutor<T> {

    public A processStatement(ArrayStatement statement) throws QueryExecutorException;

}


/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.ISerializer;
import com.redshape.persistence.dao.SerializationException;
import com.redshape.persistence.dao.query.executors.AbstractQueryExecutor;
import com.redshape.persistence.dao.query.executors.IDynamicQueryExecutor;
import com.redshape.persistence.dao.query.expressions.*;
import com.redshape.persistence.dao.query.expressions.operations.BinaryOperation;
import com.redshape.persistence.dao.query.expressions.operations.UnaryOperation;
import com.redshape.persistence.dao.query.statements.*;
import org.apache.hadoop.hbase.filter.*;


/**
 * User: cwiz
 * Date: 23.11.10
 * Time: 21:28
 */
public class HBaseQueryExecutor extends AbstractQueryExecutor<Filter, Filter, Object>
        implements IDynamicQueryExecutor<Filter, byte[]> {

    private ISerializer serializer;
    private byte[] coulmnFamily;

    public HBaseQueryExecutor(IQuery query, ISerializer serializer) throws SerializationException {
        super(query);

        this.serializer = serializer;

        this.init();
    }

    @Deprecated
    public byte[] getColumnFamily() {
        return this.coulmnFamily;
    }

    private void init() throws SerializationException {
        this.coulmnFamily = serializer.serialize("redshape");
    }

    public ISerializer getSerializer() {
        return this.serializer;
    }

    public void setSerializer(ISerializer serializer) throws SerializationException {
        this.serializer = serializer;
        this.init();
    }

    @Override
    protected Filter processResult( Filter filter ) {
        return filter;
    }

    @Override
    public Object processStatement(IAliasStatement statement) throws QueryExecutorException {
        throw new UnsupportedOperationException("Aliases not supported");
    }

    @Override
    public Filter processExpression(EqualsOperation equals) throws QueryExecutorException {
        try {
            Object left = ((ScalarStatement) equals.getLeftOperand()).getValue();
            Object right = ((ScalarStatement) equals.getRightOperand()).getValue();

            if (right instanceof String) {
                String rightVal = (String) ((ScalarStatement) equals.getRightOperand()).getValue();
                right = this.getQuery().getAttribute(rightVal);
            }

            return new SingleColumnValueFilter(
                    coulmnFamily,
                    serializer.serialize(left),
                    CompareFilter.CompareOp.EQUAL,
                    serializer.serialize(right)
            );
        } catch (SerializationException e) {
            throw new QueryExecutorException(e.getMessage());
        }
    }

    @Override
    public Filter processExpression(LessThanOperation less) throws QueryExecutorException {

        try {
            Object left = ((ScalarStatement) less.getLeftOperand()).getValue();
            Object right = ((ScalarStatement) less.getRightOperand()).getValue();

            if (right instanceof String) {
                String rightVal = (String) ((ScalarStatement) less.getRightOperand()).getValue();
                right = this.getQuery().getAttribute(rightVal);
            }

            return new SingleColumnValueFilter(
                    coulmnFamily,
                    serializer.serialize(left),
                    CompareFilter.CompareOp.LESS_OR_EQUAL,
                    serializer.serialize(right)
            );
        } catch (SerializationException e) {
            throw new QueryExecutorException(e.getMessage());
        }
    }

    @Override
    public Filter processExpression(GreaterThanOperation greater) throws QueryExecutorException {
        try {
            Object left = ((ScalarStatement) greater.getLeftOperand()).getValue();
            Object right = ((ScalarStatement) greater.getRightOperand()).getValue();

            if (right instanceof String) {
                String rightVal = (String) ((ScalarStatement) greater.getRightOperand()).getValue();
                right = this.getQuery().getAttribute(rightVal);
            }

            return new SingleColumnValueFilter(
                    coulmnFamily,
                    serializer.serialize(left),
                    CompareFilter.CompareOp.GREATER_OR_EQUAL,
                    serializer.serialize(right)
            );
        } catch (SerializationException e) {
            throw new QueryExecutorException(e.getMessage());
        }
    }

    @Override
    public Filter processExpression(AndExpression and) throws QueryExecutorException {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        IExpression[] terms = and.getTerms();
        for (IExpression term : terms) {
            Filter filter = this.processExpression(term);
            filterList.addFilter(filter);
        }

        return filterList;
    }

    @Override
    public Filter processExpression(OrExpression or) throws QueryExecutorException {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        IExpression[] terms = or.getTerms();
        for (IExpression term : terms) {
            filterList.addFilter(this.processExpression(term));
        }

        return filterList;
    }

    @Override
    public Filter processExpression(NotOperation not) throws QueryExecutorException {
        SkipFilter skipFilter = new SkipFilter(processExpression(not.getExpression()));
        return skipFilter;
    }

    @Override
    public Object processExpression(InExpression expression) throws QueryExecutorException {
        throw new UnsupportedOperationException("Operation not supported currently");
    }

    @Override
    public Object processExpression(LikeExpression expression) throws QueryExecutorException {
        throw new UnsupportedOperationException("Operation not supported currently");
    }

    @Override
    public Object processExpression(UnaryOperation operation) throws QueryExecutorException {
        throw new UnsupportedOperationException("Operation not supported currently");
    }

    @Override
    public Object processExpression(BinaryOperation operation) throws QueryExecutorException {
        throw new UnsupportedOperationException("Operation not supported currently");
    }

    @Override
    public Object processStatement( JoinStatement statement ) throws QueryExecutorException {
        throw new UnsupportedOperationException("Operation not supported currently");
    }

    @Override
    public Object processExpression(FunctionExpression expression) throws QueryExecutorException {
        throw new UnsupportedOperationException("Operation not supported currently");
    }

    @Override
    public byte[] processStatement(IArrayStatement statement) throws QueryExecutorException {
        Object[] objects = new Object[ statement.getSize() ];
        int i = 0;
        for ( IStatement item : statement.getStatements() ) {
            objects[i++] = this.processStatement(item);
        }
        
        try {
            return this.getSerializer().serialize( objects );
        } catch ( SerializationException e ) {
            throw new QueryExecutorException("Failed to serialized arrays of objects", e );
        }
    }

    @Override
    public Object processStatement(ScalarStatement<?> scalar) {
        return scalar.getValue() ;
    }

    @Override
    public Object processStatement(ReferenceStatement reference) throws QueryExecutorException {
        if ( !this.getQuery().hasAttribute( reference.getValue() ) ) {
            throw new QueryExecutorException("Unknown attribute exception!");
        }

        return this.getQuery().getAttribute(reference.getValue());
    }
}

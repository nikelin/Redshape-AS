package com.redshape.persistence.dao.query.expressions;

/**
 * User: cwiz
 * Date: 19.11.10
 * Time: 17:29
 */
public class NotOperation implements IExpression {
    IExpression expression;

    public NotOperation(IExpression expression) {
        this.expression = expression;
    }

    public IExpression getExpression() {
        return this.expression;
    }

}


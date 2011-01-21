package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IStatement;

public class EqualsOperation implements IExpression {
    protected IStatement left;
    protected IStatement right;

    public EqualsOperation(IStatement left, IStatement right) {
        this.left = left;
        this.right = right;
    }

    public IStatement getLeftOperand() {
        return this.left;
    }

    public IStatement getRightOperand() {
        return this.right;
    }
}

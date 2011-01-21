package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IStatement;

public class LessThanOperation implements IExpression {
    protected IStatement left;
    protected IStatement right;

    public LessThanOperation(IStatement left, IStatement right) {
        this.left = left;
        this.right = right;
    }

    public <T extends IStatement> T getLeftOperand() {
        return (T) this.left;
    }

    public <T extends IStatement> T getRightOperand() {
        return (T) this.right;
    }
}


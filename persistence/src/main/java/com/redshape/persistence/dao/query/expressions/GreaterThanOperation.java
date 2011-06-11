package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IStatement;

public class GreaterThanOperation implements IExpression {
    protected IStatement left;
    protected IStatement right;

    public GreaterThanOperation(IStatement left, IStatement right) {
        this.left = left;
        this.right = right;
    }

    @SuppressWarnings("unchecked")
	public <T extends IStatement> T getLeftOperand() {
        return (T) this.left;
    }

    @SuppressWarnings("unchecked")
	public <T extends IStatement> T getRightOperand() {
        return (T) this.right;
    }
}


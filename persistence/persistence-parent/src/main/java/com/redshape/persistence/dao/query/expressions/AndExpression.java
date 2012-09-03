package com.redshape.persistence.dao.query.expressions;

public class AndExpression implements IExpression {
    IExpression[] terms;

    public AndExpression(IExpression... terms) {
        this.terms = terms;
    }

    @SuppressWarnings("unchecked")
	public <T extends IExpression> T[] getTerms() {
        return (T[]) terms;
    }
}


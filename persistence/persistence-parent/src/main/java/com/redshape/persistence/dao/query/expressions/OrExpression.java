package com.redshape.persistence.dao.query.expressions;

public class OrExpression implements IExpression {
    IExpression[] terms;

    public OrExpression(IExpression... terms) {
        this.terms = terms;
    }

    @SuppressWarnings("unchecked")
	public <T extends IExpression> T[] getTerms() {
        return (T[]) terms;
    }
}

package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * @package com.redshape.persistence.dao.query.expressions
 * @user cyril
 * @date 7/18/11 1:22 PM
 */
public class FunctionExpression implements IExpression {
    private String name;
    private IStatement[] terms;

    public FunctionExpression( String name, IStatement... terms ) {
        this.name = name;
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IStatement[] getTerms() {
        return terms;
    }

    public void setTerms(IStatement[] terms) {
        this.terms = terms;
    }
}

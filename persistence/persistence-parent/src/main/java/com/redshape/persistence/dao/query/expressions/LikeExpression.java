package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class LikeExpression implements IExpression {
    private IStatement field;
    private IStatement mask;
    
    public LikeExpression( IStatement field, IStatement mask ) {
        this.field = field;
        this.mask = mask;
    }

    public IStatement getField() {
        return field;
    }

    public IStatement getMask() {
        return mask;
    }
}

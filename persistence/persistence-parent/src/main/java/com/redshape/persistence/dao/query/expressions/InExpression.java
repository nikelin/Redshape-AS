package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IArrayStatement;
import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class InExpression implements IExpression {

    private IArrayStatement range;
    private IStatement field;
    
    public InExpression( IStatement field, IArrayStatement range ) {
        this.field = field;
        this.range = range;
    }

    public IArrayStatement getRange() {
        return range;
    }

    public IStatement getField() {
        return field;
    }
}

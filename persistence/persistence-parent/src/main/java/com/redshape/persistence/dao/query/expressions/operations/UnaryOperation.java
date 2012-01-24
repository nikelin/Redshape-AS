package com.redshape.persistence.dao.query.expressions.operations;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * @package com.redshape.persistence.dao.query.expressions.operations
 * @user cyril
 * @date 7/19/11 5:08 PM
 */
public class UnaryOperation implements IExpression {
    public enum Types {
        NEGATE
    }

    private Types type;
    private IStatement term;

    public UnaryOperation( Types type, IStatement statement ) {
        this.term = statement;
    }

    public Types getType() {
        return type;
    }

    public IStatement getTerm() {
        return this.term;
    }

}

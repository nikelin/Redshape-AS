package com.redshape.persistence.dao.query.expressions.operations;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * @package com.redshape.persistence.dao.query.expressions.operations
 * @user cyril
 * @date 7/19/11 5:08 PM
 */
public class BinaryOperation implements IExpression {
    public enum Types {
        SUBTRACT, SUM, DIVIDE, PROD, MOD
    }

    private Types type;
    private IStatement left;
    private IStatement right;

    public BinaryOperation( Types type, IStatement left, IStatement right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public Types getType() {
        return type;
    }

    public IStatement getLeft() {
        return left;
    }

    public IStatement getRight() {
        return right;
    }
}

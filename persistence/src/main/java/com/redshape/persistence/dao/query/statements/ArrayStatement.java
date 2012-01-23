package com.redshape.persistence.dao.query.statements;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayStatement implements IArrayStatement {
    private IStatement[] statements;
    
    public ArrayStatement(IStatement... statements) {
        this.statements = statements;
    }

    @Override
    public int getSize() {
        return this.statements.length;
    }

    @Override
    public IStatement getStatement( int statement ) {
        if ( statement < 0 || statement > this.statements.length ) {
            throw new ArrayIndexOutOfBoundsException(statement);
        }

        return this.statements[statement];
    }

    @Override
    public IStatement[] getStatements() {
        return this.statements;
    }
    
}

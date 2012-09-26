package com.redshape.persistence.dao.query.statements;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IArrayStatement extends IStatement {
    
    public int getSize();
    
    public IStatement getStatement( int index );
    
    public IStatement[] getStatements();
    
}

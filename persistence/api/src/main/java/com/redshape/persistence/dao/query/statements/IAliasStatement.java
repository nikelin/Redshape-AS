package com.redshape.persistence.dao.query.statements;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/15/12
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAliasStatement extends IStatement {

    public boolean doEvaluateSource();

    public IStatement getSource();

    public String getTarget();

}

package com.redshape.persistence.dao.query.statements;


/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/15/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IJoinStatement extends IStatement {

    public enum JoinEntityType {
        COLLECTION,
        SINGULAR
    }

    public enum JoinType {
        INNER,
        LEFT,
        RIGHT
    }

    public JoinType getJoinType();

    public JoinEntityType getJoinEntityType();

    public String getName();

    public String getAlias();

}

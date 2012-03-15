package com.redshape.persistence.dao.query.statements;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/15/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinStatement implements IJoinStatement {
    private String name;
    private JoinType joinType;
    private JoinEntityType entityType;
    private IStatement reference;

    public JoinStatement( JoinEntityType entityType, JoinType joinType, String name ) {
        this.name = name;
        this.joinType = joinType;
        this.entityType = entityType;
    }

    @Override
    public JoinType getJoinType() {
        return this.joinType;
    }

    @Override
    public JoinEntityType getJoinEntityType() {
        return this.entityType;
    }

    @Override
    public String getName() {
        return this.name;
    }

}

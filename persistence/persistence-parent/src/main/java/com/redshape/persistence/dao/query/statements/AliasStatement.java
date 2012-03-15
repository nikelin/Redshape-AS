package com.redshape.persistence.dao.query.statements;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/15/12
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class AliasStatement implements IAliasStatement {
    private IStatement source;
    private String target;
    private boolean evaluateSource;

    public AliasStatement(IStatement source, String target) {
        this(source, target, false);
    }

    public AliasStatement(IStatement source, String target, boolean evaluateSource ) {
        this.source = source;
        this.evaluateSource = evaluateSource;
        this.target = target;
    }

    @Override
    public boolean doEvaluateSource() {
        return this.evaluateSource;
    }

    @Override
    public IStatement getSource() {
        return this.source;
    }

    @Override
    public String getTarget() {
        return this.target;
    }
}

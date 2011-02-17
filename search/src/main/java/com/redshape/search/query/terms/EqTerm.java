package com.redshape.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 11:58:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class EqTerm implements IBinaryTerm {
    private LiteralTerm term;
    private LiteralTerm value;

    public EqTerm( LiteralTerm term, LiteralTerm value ) {
        this.term = term;
        this.value = value;
    }

    public LiteralTerm getLeft() {
        return this.term;
    }

    public LiteralTerm getRight() {
        return this.value;
    }

    public Operation getOperation() {
        return Operation.EQUALS;
    }

}

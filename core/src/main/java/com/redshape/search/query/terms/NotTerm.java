package com.redshape.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 11:55:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotTerm implements IUnaryTerm {
    private ISearchTerm term;

    public NotTerm( ISearchTerm term ) {
        this.term = term;
    }

    public ISearchTerm getTerm() {
        return this.term;
    }

    public Operation getOperation() {
        return Operation.NOT;
    }

}

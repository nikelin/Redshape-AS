package com.vio.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 11:56:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class AndTerm implements IBinaryTerm {
    private ISearchTerm first;
    private ISearchTerm second;

    public AndTerm( ISearchTerm first, ISearchTerm second ) {
        this.first = first;
        this.second = second;
    }

    public ISearchTerm getLeft() {
        return this.first;
    }

    public ISearchTerm getRight() {
        return this.second;
    }

    public Operation getOperation() {
        return Operation.AND;
    }

}

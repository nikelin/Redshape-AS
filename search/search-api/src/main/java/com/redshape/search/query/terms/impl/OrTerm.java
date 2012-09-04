package com.redshape.search.query.terms.impl;

import com.redshape.search.query.terms.IBinaryTerm;
import com.redshape.search.query.terms.ISearchTerm;
import com.redshape.search.query.terms.Operation;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 11:57:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrTerm implements IBinaryTerm {
    private ISearchTerm first;
    private ISearchTerm second;

    public OrTerm() {
        this(null, null);
    }

    public OrTerm( ISearchTerm first, ISearchTerm second ) {
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
        return Operation.OR;
    }

}

package com.redshape.search.query.terms.impl;

import com.redshape.search.query.terms.IBinaryTerm;
import com.redshape.search.query.terms.IUnaryTerm;
import com.redshape.search.query.terms.Operation;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 11:58:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class EqTerm implements IBinaryTerm {
    private IUnaryTerm term;
    private IUnaryTerm value;

    public EqTerm() {
        this(null, null);
    }

    public EqTerm( IUnaryTerm term, IUnaryTerm value ) {
        this.term = term;
        this.value = value;
    }

    public IUnaryTerm getLeft() {
        return this.term;
    }

    public IUnaryTerm getRight() {
        return this.value;
    }

    public Operation getOperation() {
        return Operation.EQUALS;
    }

}

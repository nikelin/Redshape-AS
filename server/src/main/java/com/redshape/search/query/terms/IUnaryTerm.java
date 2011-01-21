package com.redshape.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 2:23:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IUnaryTerm extends ISearchTerm {

    public ISearchTerm getTerm();

    public Operation getOperation();

}

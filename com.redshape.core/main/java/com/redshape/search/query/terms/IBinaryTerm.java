package com.redshape.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 2:23:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBinaryTerm extends ISearchTerm {

    public ISearchTerm getLeft();

    public ISearchTerm getRight();

    public Operation getOperation();

}

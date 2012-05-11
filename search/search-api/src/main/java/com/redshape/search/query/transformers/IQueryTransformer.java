package com.redshape.search.query.transformers;

import com.redshape.search.query.terms.ISearchTerm;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 12:11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IQueryTransformer {

    public <T> T transform( ISearchTerm term ) throws QueryTransformationException;

}
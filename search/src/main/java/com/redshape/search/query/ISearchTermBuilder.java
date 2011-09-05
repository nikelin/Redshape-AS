package com.redshape.search.query;

import com.redshape.search.query.terms.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query
 * @date 9/5/11 2:06 PM
 */
public interface ISearchTermBuilder {

	public IBinaryTerm  and( ISearchTerm left, ISearchTerm right );

    public IBinaryTerm  or( ISearchTerm left, ISearchTerm right );

    public IUnaryTerm  not( ISearchTerm operand );

    public IBinaryTerm  eq( IUnaryTerm operand, IUnaryTerm value );

    public IScalarTerm literal( Object value );
}

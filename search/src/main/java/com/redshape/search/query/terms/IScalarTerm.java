package com.redshape.search.query.terms;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/5/11 2:10 PM
 */
public interface IScalarTerm extends ISearchTerm {

	public <T> T getValue();

}

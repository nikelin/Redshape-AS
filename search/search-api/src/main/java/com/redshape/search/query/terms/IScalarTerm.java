package com.redshape.search.query.terms;

/**
 * Term which represents single scalar value
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/5/11 2:10 PM
 */
public interface IScalarTerm extends ISearchTerm {

	/**
	 * Return value
	 * @param <T>
	 * @return
	 */
	public <T> T getValue();

}

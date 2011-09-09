package com.redshape.search.query.terms;

/**
 * Represents single term with applied operation.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public interface IUnaryTerm extends ISearchTerm {

	/**
	 * Return term
	 * @return
	 */
    public ISearchTerm getTerm();

	/**
	 * Return operation which applied to the underlying term
	 * @return
	 */
	public Operation getOperation();

}

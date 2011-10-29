package com.redshape.search.query.terms;

/**
 * Represents binary search term.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public interface IBinaryTerm extends ISearchTerm {

	/**
	 * Return left member of a binary group
	 * @return
	 */
    public <T extends ISearchTerm> T getLeft();

	/**
	 * Return right member of a binary group
	 * @return
	 */
    public <T extends ISearchTerm> T getRight();

	/**
	 * Return operation which join binary group
	 * @return
	 */
	public Operation getOperation();

}

package com.redshape.search.query.terms;

/**
 * Represents set of available operation might be applied
 * to a some of available search terms.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public enum Operation {
	/**
	* @see com.redshape.search.query.terms.AndTerm
	*/
    AND,
	/**
	 * @see com.redshape.search.query.terms.OrTerm
	 */
    OR,
	/**
	 * @see com.redshape.search.query.terms.NotTerm
	 */
    NOT,
	/**
	 * @see com.redshape.search.query.terms.EqTerm
	 */
    EQUALS,
	/**
	 * @see com.redshape.search.query.terms.ToTerm
	 */
	TO
}

package com.redshape.search.query.terms;

/**
 * Represents concrete field constraints
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:51 PM
 */
public interface IFieldTerm extends ISearchTerm {

	/**
	 * Return affected search entity field
	 * @return
	 */
	public String getField();

	/**
	 * Return term [list] which was applied to a field
	 *
	 * @return
	 */
	public ISearchTerm getTerm();

}

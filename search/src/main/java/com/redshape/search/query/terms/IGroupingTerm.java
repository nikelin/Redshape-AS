package com.redshape.search.query.terms;

/**
 * Represents group of single field clauses as described
 * in  {@link ##http://lucene.apache.org/java/2_4_0/queryparsersyntax.html## }
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public interface IGroupingTerm extends ISearchTerm {

	/**
	 * List field terms which added to this group
	 *
	 * @return
	 */
	public ISearchTerm[] getList();

	public Operation getOperation();

}

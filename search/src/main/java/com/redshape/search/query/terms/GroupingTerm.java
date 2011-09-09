package com.redshape.search.query.terms;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public class GroupingTerm implements IGroupingTerm {
	private ISearchTerm[] terms;
	private Operation operation;

	public GroupingTerm( ISearchTerm[] terms, Operation operation ) {
		this.terms = terms;
		this.operation = operation;
	}

	@Override
	public Operation getOperation() {
		return this.operation;
	}

	@Override
	public ISearchTerm[] getList() {
		return this.terms;
	}
}

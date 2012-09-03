package com.redshape.search.query.terms;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:53 PM
 */
public class ToTerm implements IBinaryTerm {
	private ISearchTerm from;
	private ISearchTerm to;
	private RangeType rangeType;

	public ToTerm( ISearchTerm from, ISearchTerm to, RangeType constraint ) {
		this.from = from;
		this.to = to;
		this.rangeType = constraint;
	}

	@Override
	public ISearchTerm getLeft() {
		return this.from;
	}

	@Override
	public ISearchTerm getRight() {
		return this.to;
	}

	@Override
	public Operation getOperation() {
		return Operation.TO;
	}

	public RangeType getType() {
		return this.rangeType;
	}
}

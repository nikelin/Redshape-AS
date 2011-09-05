package com.redshape.search.query;

import com.redshape.search.query.terms.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query
 * @date 9/5/11 2:17 PM
 */
public class SearchTermBuilder implements ISearchTermBuilder {

	@Override
	public IBinaryTerm and(ISearchTerm left, ISearchTerm right) {
		return new AndTerm(left, right);
	}

	@Override
	public IBinaryTerm or(ISearchTerm left, ISearchTerm right) {
		return new OrTerm(left, right);
	}

	@Override
	public IUnaryTerm not(ISearchTerm operand) {
		return new NotTerm(operand);
	}

	@Override
	public IBinaryTerm eq(IUnaryTerm operand, IUnaryTerm value) {
		return new EqTerm(operand, value);
	}

	@Override
	public IScalarTerm literal(Object value) {
		return new LiteralTerm(value);
	}
}

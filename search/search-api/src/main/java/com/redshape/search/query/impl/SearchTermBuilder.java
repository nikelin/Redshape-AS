package com.redshape.search.query.impl;

import com.redshape.search.query.ISearchTermBuilder;
import com.redshape.search.query.terms.*;
import com.redshape.search.query.terms.impl.*;

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
	public IFieldTerm field(String name, ISearchTerm term) {
		return new FieldTerm(name, term);
	}

	@Override
	public IGroupingTerm field(String[] names, ISearchTerm term, Operation operation ) {
		ISearchTerm[] terms = new ISearchTerm[ names.length ];
		for ( int i = 0; i < names.length; i++ ) {
			terms[i] = this.field(names[i], term);
		}

		return this.group( operation, terms );
	}

	@Override
	public IGroupingTerm group( Operation operation, ISearchTerm... list) {
		return new GroupingTerm(list, operation);
	}

	@Override
	public IBinaryTerm to(ISearchTerm from, ISearchTerm to, RangeType type) {
		return new ToTerm(from, to, type);
	}

	@Override
	public IScalarTerm literal(Object value) {
		return new LiteralTerm(value);
	}
}

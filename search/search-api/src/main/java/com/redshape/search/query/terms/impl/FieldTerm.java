package com.redshape.search.query.terms.impl;

import com.redshape.search.query.terms.IFieldTerm;
import com.redshape.search.query.terms.ISearchTerm;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 2:14 PM
 */
public class FieldTerm implements IFieldTerm {
	private String fieldName;
	private ISearchTerm term;

    public FieldTerm() {
        this(null, null);
    }

    public FieldTerm( String fieldName, ISearchTerm term ) {
		this.fieldName = fieldName;
		this.term = term;
	}

	@Override
	public String getField() {
		return this.fieldName;
	}

	@Override
	public ISearchTerm getTerm() {
		return this.term;
	}
}

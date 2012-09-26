package com.redshape.search.query.terms;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 2:26 PM
 */
public enum RangeType {
	EXCLUSIVE("[", "]"),
	INCLUSIVE("{", "}");

	private String openSymbol;
	private String closingSymbol;

	private RangeType( String openSymbol, String closingSymbol ) {
		this.openSymbol = openSymbol;
		this.closingSymbol = closingSymbol;
	}

	public String openSymbol() {
		return this.openSymbol;
	}

	public String closingSymbol() {
		return this.closingSymbol;
	}
}

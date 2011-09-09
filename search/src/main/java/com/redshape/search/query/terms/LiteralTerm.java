package com.redshape.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 1:54:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiteralTerm implements IScalarTerm {
    private Object value;

    public LiteralTerm( Object value ) {
        this.value = value;
    }

	@Override
	public <T> T getValue() {
		return (T) this.value;
	}

}

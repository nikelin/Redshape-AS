package com.vio.search.query.terms;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 1:54:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiteralTerm implements ISearchTerm {
    private String value;

    public LiteralTerm( String value ) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

}

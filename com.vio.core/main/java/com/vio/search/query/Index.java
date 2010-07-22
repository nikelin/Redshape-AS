package com.vio.search.query;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:54:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Index {
    private Set<IndexField> fields;

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public IndexField getField( String name ) {
        for ( IndexField field : this.getFields() ) {
            if ( field.getName().equals(name) ) {
                return field;
            }
        }

        return null;
    }

    public void addField( IndexField field ) {
        this.fields.add(field);
    }

    public Set<IndexField> getFields() {
        return this.fields;
    }

}

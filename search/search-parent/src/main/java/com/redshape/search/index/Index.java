package com.redshape.search.index;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:54:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Index implements IIndex {
    private Set<IIndexField> fields = new HashSet<IIndexField>();

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public IIndexField getField( String name ) {
        for ( IIndexField field : this.getFields() ) {
            if ( field.getName().equals(name) ) {
                return field;
            }
        }

        return null;
    }

    @Override
    public void addField( IIndexField field ) {
        this.fields.add(field);
    }

    @Override
    public Set<IIndexField> getFields() {
        return this.fields;
    }

    @Override
    public boolean hasField( String name ) {
        for ( IIndexField field : this.fields ) {
            if ( field.getName().equals(name) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IIndexField createField( String fieldName ) {
        return new IndexField( fieldName );
    }

}

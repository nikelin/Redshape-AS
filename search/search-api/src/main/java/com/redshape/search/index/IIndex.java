package com.redshape.search.index;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 2:53:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IIndex {
    
    public String getName();

    public void setName( String name );

    public IIndexField getField( String name );

    public void addField( IIndexField field );

    public Set<IIndexField> getFields();

    public IIndexField createField();

    public boolean hasField( String name );
}

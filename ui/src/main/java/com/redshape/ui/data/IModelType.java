package com.redshape.ui.data;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public interface IModelType {

    public int count();
    
    public int nonTransientCount();

    public IModelField getField( int index );

    public IModelField getField( String name );

    public IModelField addField( String name );

    public void removeField( String name );

    public Collection<IModelField> getFields();

    public IModelData createRecord();

}

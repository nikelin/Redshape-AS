package com.vio.search.index.builders;

import com.vio.search.ISearchable;
import com.vio.search.index.visitor.field.IFieldVisitor;
import com.vio.search.index.IIndex;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 2:53:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IIndexBuilder {

    public IIndex getIndex( Class<? extends ISearchable> searchable ) throws BuilderException;

    public IFieldVisitor getFieldVisitor();

    public void setFieldVisitor( IFieldVisitor visitor );

}

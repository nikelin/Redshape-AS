package com.redshape.search.index.builders;

import com.redshape.search.ISearchable;
import com.redshape.search.index.visitor.field.IFieldVisitor;
import com.redshape.search.index.IIndex;

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

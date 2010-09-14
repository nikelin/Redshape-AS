package com.redshape.search.index.builders;

import com.redshape.search.index.visitor.field.IFieldVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 3:18:51 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIndexBuilder implements IIndexBuilder {
    private IFieldVisitor fieldVisitor;

    @Override
    public IFieldVisitor getFieldVisitor() {
        return this.fieldVisitor;
    }

    @Override
    public void setFieldVisitor( IFieldVisitor visitor ) {
        this.fieldVisitor = visitor;
    }

}

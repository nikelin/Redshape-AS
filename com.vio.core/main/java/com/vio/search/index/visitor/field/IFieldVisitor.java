package com.vio.search.index.visitor.field;

import com.vio.search.ISearchable;
import com.vio.search.index.IIndex;
import com.vio.search.index.visitor.VisitorException;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 2:51:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFieldVisitor {

    public void visitField( IIndex index, Class<? extends ISearchable> entityClass, Field field ) throws VisitorException;

}

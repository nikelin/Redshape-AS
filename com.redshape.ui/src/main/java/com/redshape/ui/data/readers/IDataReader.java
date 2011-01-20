package com.redshape.ui.data.readers;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelType;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public interface IDataReader<V extends IModelData, T> extends IReader<T, V> {

    public void setType( IModelType type );

    public IModelType getType();

}

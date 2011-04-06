package com.redshape.ui.data.readers;

import com.redshape.ui.data.IModelData;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public interface IListReader<V extends IModelData, T> extends IDataReader<V, T> {

    public Collection<V> processList( T source ) throws ReaderException;

}

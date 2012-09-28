package com.redshape.ui.data.readers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
public interface IReader<V, T> {

    public T process( V source ) throws ReaderException;

}

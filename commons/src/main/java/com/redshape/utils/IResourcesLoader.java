package com.redshape.utils;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: Nov 8, 2010
 * Time: 5:27:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IResourcesLoader<T> {

    public T getResource( String path ) throws IOException; 

}

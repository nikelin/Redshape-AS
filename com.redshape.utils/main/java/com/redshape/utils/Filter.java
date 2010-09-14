package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 22, 2010
 * Time: 1:47:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Filter<T> {

    public boolean filter( T filterable );
    
}

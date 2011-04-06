package com.redshape.ui.data.loaders;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
public interface ILoaderConfig {

    public void setTimeout( int timeout );

    public int getTimeout();

    public void setParameter( String name, Object value );

    public <V> V getParameter( String name );

}

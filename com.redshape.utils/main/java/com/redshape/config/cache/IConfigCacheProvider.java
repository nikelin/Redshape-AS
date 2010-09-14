package com.redshape.config.cache;

import com.redshape.config.IConfig;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 10, 2010
 * Time: 12:32:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfigCacheProvider {

    public void getAttribute( IConfig node, String name ) throws ConfigCacheException;

    public void cacheAttribute( IConfig node, String name, String value ) throws ConfigCacheException;

    public void cacheValue( IConfig node, String value ) throws ConfigCacheException;

    public void cacheList( IConfig node, String[] values ) throws ConfigCacheException;

    public void cacheNames( IConfig node, String[] names ) throws ConfigCacheException;

}

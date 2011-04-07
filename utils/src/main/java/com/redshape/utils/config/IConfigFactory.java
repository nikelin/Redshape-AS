package com.redshape.utils.config;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 14, 2010
 * Time: 12:48:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfigFactory {

    public void setImpl( Class<? extends IConfig> config );

    public <T extends IConfig> Class<T> getImpl();

    public IConfig createConfig( String path ) throws InstantiationException;

    public IConfig createConfig( File path ) throws InstantiationException;

}

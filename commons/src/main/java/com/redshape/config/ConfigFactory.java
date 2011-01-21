package com.redshape.config;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 14, 2010
 * Time: 12:49:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigFactory implements IConfigFactory {
    private static IConfigFactory defaultInstance = new ConfigFactory();
    private Class<? extends IConfig> configImpl = XMLConfig.class;

    public static IConfigFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( IConfigFactory instance ) {
        defaultInstance = instance;
    }

    public void setImpl( Class<? extends IConfig> impl ) {
        this.configImpl = impl;
    }

    public <T extends IConfig> Class<T> getImpl() {
        return (Class<T>) this.configImpl;
    }

    public IConfig createConfig( File file ) throws InstantiationException {
        if ( this.getImpl() == null ) {
            throw new InstantiationException("Factory configuration exception");
        }

        try {
            return this.getImpl().getConstructor( file.getClass() ).newInstance(file);
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public IConfig createConfig( String path ) throws InstantiationException {
        if ( this.getImpl() == null ) {
            throw new InstantiationException("Factory configuration exception");
        }

        try {
            return this.getImpl().getConstructor( path.getClass() ).newInstance(path);
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

}

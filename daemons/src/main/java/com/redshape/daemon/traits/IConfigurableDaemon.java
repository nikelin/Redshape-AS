package com.redshape.daemon.traits;

import com.redshape.daemon.DaemonException;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

public interface IConfigurableDaemon<T> 
							extends IDaemon<T> {

    /**
     * Reloads com.redshape.daemon configuration
     *
     * @param config - location for Spring Bean Configuration file from the classpath
     * @throws DaemonException
     */
    public void reloadConfiguration(IConfig config) throws DaemonException, ConfigException;

    public void loadConfiguration() throws DaemonException, ConfigException;

    public void loadConfiguration(IConfig configLocation) throws DaemonException, ConfigException;

    public boolean isConfigured();

    public void setConfigured( boolean value );
    
}

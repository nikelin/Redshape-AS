/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

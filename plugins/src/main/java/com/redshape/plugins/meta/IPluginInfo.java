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

package com.redshape.plugins.meta;

import com.redshape.plugins.packagers.IPackageDescriptor;
import com.redshape.plugins.packagers.IPackageStarter;
import com.redshape.plugins.starters.EngineType;

import java.security.Permission;
import java.util.List;
import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 12:19 PM
 */
public interface IPluginInfo {

    /**
     * Return plugin name
     * @return
     */
    public String getName();

    /**
     * Get permissions requested by the plugin
     * @return
     */
    public Set<Permission> getPermissions();

	/**
	 * Return version of Plugins API under which
	 * current plugin based on.
	 * @return
	 */
	public String getArchVersion();

    /**
     * Return plugin publisher details
     * @return
     */
	public IPublisherInfo getPublisher();

    /**
     * Get information about starter which plugin
     * require
     * @return
     */
    public IPackageStarter getStarterInfo();

    /**
     * Return package descriptor related to this
     * plugin
     * @return
     */
	public IPackageDescriptor getPackageDescriptor();

    /**
     * Create empty publisher info data object
     * @return
     */
    public IPublisherInfo createPublisherInfo();

    /**
     * Get plugin main class path
     * @return
     */
    public String getEntryPoint();

    /**
     * Create empty starter info data object
     * @param type
     * @param version
     * @return
     */
    public IPackageStarter createStarterInfo( EngineType type, String version );

}

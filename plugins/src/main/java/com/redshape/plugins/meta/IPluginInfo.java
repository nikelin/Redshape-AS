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

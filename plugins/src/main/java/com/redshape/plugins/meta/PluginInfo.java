package com.redshape.plugins.meta;

import com.redshape.plugins.packagers.IPackageDescriptor;
import com.redshape.plugins.packagers.IPackageStarter;
import com.redshape.plugins.starters.EngineType;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta.data
 * @date 10/11/11 4:51 PM
 */
class PluginInfo implements IPluginInfo {
	private String entryPoint;
	private String sourcesPath;
	private String resourcesPath;
    private String name;
    private IPackageStarter starterInfo;
	private Set<Permission> permissions = new HashSet<Permission>();
	private IPackageDescriptor descriptor;
	private IPublisherInfo publisher;
	private String archVersion;

	public PluginInfo( IPackageDescriptor descriptor ) {
		this.descriptor = descriptor;
	}

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getSourcesPath() {
        return sourcesPath;
    }

    public void setSourcesPath(String sourcesPath) {
        this.sourcesPath = sourcesPath;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
    
    public void addPermission( Permission permission ) {
        this.permissions.add( permission );
    }

    public void setPublisher(IPublisherInfo publisher) {
		this.publisher = publisher;
	}

	public void setArchVersion(String archVersion) {
		this.archVersion = archVersion;
	}

	@Override
	public IPackageDescriptor getPackageDescriptor() {
		return this.descriptor;
	}

	@Override
	public IPublisherInfo getPublisher() {
		return this.publisher;
	}

	@Override
	public String getArchVersion() {
		return this.archVersion;
	}

    @Override
    public String getName() {
        return this.name;
    }

    public void setStarterInfo( IPackageStarter starter ) {
        this.starterInfo = starter;
    }

    @Override
    public IPackageStarter getStarterInfo() {
        return this.starterInfo;
    }

    @Override
    public IPublisherInfo createPublisherInfo() {
        return new PublisherInfo();
    }

    @Override
    public IPackageStarter createStarterInfo(EngineType type, String version) {
        return new PackageStarter(type, version);
    }
}

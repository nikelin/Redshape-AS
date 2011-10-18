package com.redshape.plugins.meta.data;

import com.redshape.plugins.meta.IPluginInfo;
import com.redshape.plugins.meta.IPublisherInfo;
import com.redshape.plugins.packagers.IPackageDescriptor;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta.data
 * @date 10/11/11 4:51 PM
 */
public class PluginInfo implements IPluginInfo {
	private String entryPoint;
	private String sourcesPath;
	private String resourcesPath;
	private Set<Permission> permissions = new HashSet<Permission>();
	private IPackageDescriptor descriptor;
	private IPublisherInfo publisher;
	private String archVersion;

	public PluginInfo( IPackageDescriptor descriptor ) {
		this.descriptor = descriptor;
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
}

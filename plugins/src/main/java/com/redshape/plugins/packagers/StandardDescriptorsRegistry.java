package com.redshape.plugins.packagers;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class StandardDescriptorsRegistry implements IDescriptorsRegistry {
    private Map<IPluginResource, IPackageDescriptor> registry = new HashMap<IPluginResource, IPackageDescriptor>();

    @Override
    public IPackageDescriptor getDescriptor(IPluginResource resource) {
        return this.registry.get(resource);
    }

    @Override
    public IPackageDescriptor createDescriptor(PackagingType type, URI uri) {
        return new PackageDescriptor( uri, type );
    }

    @Override
    public void registerDescriptor(IPluginResource resource, IPackageDescriptor descriptor) {
        this.registry.put(resource, descriptor);
    }
}

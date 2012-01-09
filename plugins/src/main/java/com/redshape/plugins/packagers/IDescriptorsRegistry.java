package com.redshape.plugins.packagers;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public interface IDescriptorsRegistry {

    public IPackageDescriptor getDescriptor( IPluginResource resource );

    public IPackageDescriptor createDescriptor( PackagingType type, URI uri );

    public void registerDescriptor( IPluginResource resource, IPackageDescriptor descriptor );

}

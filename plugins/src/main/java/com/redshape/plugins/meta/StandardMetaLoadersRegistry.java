package com.redshape.plugins.meta;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.packagers.IPackageDescriptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class StandardMetaLoadersRegistry implements IMetaLoadersRegistry {
    private Set<IMetaLoader> loaders = new HashSet<IMetaLoader>();

    public StandardMetaLoadersRegistry(Set<IMetaLoader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public boolean isSupported(IPackageDescriptor descriptor) {
        for ( IMetaLoader loader : this.loaders ) {
            if ( loader.isSupports(descriptor) ) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public IMetaLoader[] list() {
        return this.loaders.toArray( new IMetaLoader[this.loaders.size()] );
    }

    @Override
    public void registerLoader(IMetaLoader descriptor) {
        this.loaders.add(descriptor);
    }

    @Override
    public IMetaLoader selectLoader(IPackageDescriptor descriptor) throws LoaderException {
        for ( IMetaLoader loader : this.loaders ) {
            if ( loader.isSupports(descriptor) ) {
                return loader;
            }
        }

        return null;
    }
}

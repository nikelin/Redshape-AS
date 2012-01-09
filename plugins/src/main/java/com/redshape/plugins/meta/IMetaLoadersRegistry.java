package com.redshape.plugins.meta;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.packagers.IPackageDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public interface IMetaLoadersRegistry {

    public boolean isSupported( IPackageDescriptor descriptor );

    public void registerLoader( IMetaLoader descriptor );

    public IMetaLoader[] list();
    
    public IMetaLoader selectLoader( IPackageDescriptor descriptor ) throws LoaderException;

}

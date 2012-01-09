package com.redshape.plugins.packagers.security;

import com.redshape.plugins.packagers.IPackageDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public interface IPackageHasher {

    public byte[] calculateHash( IPackageDescriptor descriptor );

    public boolean isSame( IPackageDescriptor source, IPackageDescriptor target );

}

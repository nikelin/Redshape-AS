package com.redshape.utils;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2010
 * Time: 1:58:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPackagesLoader {

    public void addPackage( String path );

    public Collection<String> getPackages();

}

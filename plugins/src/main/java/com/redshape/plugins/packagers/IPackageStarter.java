package com.redshape.plugins.packagers;

import com.redshape.plugins.starters.EngineType;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public interface IPackageStarter {
    
    public EngineType getEngineType();
    
    public String getVersion();
    
}

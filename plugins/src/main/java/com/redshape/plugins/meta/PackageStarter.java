package com.redshape.plugins.meta;

import com.redshape.plugins.packagers.IPackageStarter;
import com.redshape.plugins.starters.EngineType;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
class PackageStarter implements IPackageStarter {
    private EngineType engineType;
    private String version;

    PackageStarter(EngineType engineType, String version) {
        this.engineType = engineType;
        this.version = version;
    }

    @Override
    public EngineType getEngineType() {
        return this.engineType;
    }

    @Override
    public String getVersion() {
        return this.version;
    }
}

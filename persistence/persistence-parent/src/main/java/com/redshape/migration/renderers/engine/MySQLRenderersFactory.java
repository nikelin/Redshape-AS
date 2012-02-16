package com.redshape.migration.renderers.engine;

import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;


/**
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.engine
 * @date Apr 6, 2010
 */
public class MySQLRenderersFactory extends AbstractRenderersFactory {

    public MySQLRenderersFactory(IConfig config,
                                 IPackagesLoader packagesLoader,
                                 IResourcesLoader resourcesLoader)
            throws ConfigException {
        super(config, packagesLoader, resourcesLoader);
    }

    public String getFactoryId() {
        return "mysql";
    }

}

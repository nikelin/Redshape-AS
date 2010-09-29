package com.redshape.migration.renderers.engine;

import com.redshape.render.*;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.Registry;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.engine
 * @date Apr 6, 2010
 */
public class MySQLRenderersFactory extends AbstractRenderersFactory {

    public MySQLRenderersFactory() {
        super( MySQLRenderersFactory.class );
    }

    public String getFactoryId() {
        return "mysql";
    }

}

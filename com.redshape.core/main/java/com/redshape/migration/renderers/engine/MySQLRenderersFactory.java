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
public class MySQLRenderersFactory extends RenderersFactory {

    protected Class<? extends Renderer>[] getRenderersClasses() throws PackageLoaderException {
        return Registry.getPackagesLoader().getClasses( "com.redshape.migration.renderers.mysql", new InterfacesFilter( new Class[] { Renderer.class }, new Class[] { TargetEntity.class }  ) );
    }

}

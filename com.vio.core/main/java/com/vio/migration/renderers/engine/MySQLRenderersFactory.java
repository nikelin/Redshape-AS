package com.vio.migration.renderers.engine;

import com.vio.render.*;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;

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
        return Registry.getPackagesLoader().getClasses( "com.vio.migration.renderers.mysql", new InterfacesFilter( new Class[] { Renderer.class }, new Class[] { TargetEntity.class }  ) );
    }

}

package com.redshape.render.json;

import com.redshape.render.*;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

public class JSONRenderersFactory extends RenderersFactory {
    private static final Logger log = Logger.getLogger( JSONRenderersFactory.class );

    public JSONRenderersFactory() {
        super();
    }

    protected Class<? extends Renderer>[] getRenderersClasses() throws PackageLoaderException {
        return Registry.getPackagesLoader().getClasses("com.redshape.render.json.renderers", new InterfacesFilter( new Class[] { Renderer.class }, new Class[] { TargetEntity.class }, true  ) );
    }
	
}
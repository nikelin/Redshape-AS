package com.vio.render.json;

import com.vio.render.*;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;

public class JSONRenderersFactory extends RenderersFactory {
    private static final Logger log = Logger.getLogger( JSONRenderersFactory.class );

    public JSONRenderersFactory() {
        super();
    }

    protected Class<? extends Renderer>[] getRenderersClasses() throws PackageLoaderException {
        return Registry.getPackagesLoader().getClasses("com.vio.render.json.renderers", new InterfacesFilter( new Class[] { Renderer.class }, new Class[] { TargetEntity.class }, true  ) );
    }
	
}
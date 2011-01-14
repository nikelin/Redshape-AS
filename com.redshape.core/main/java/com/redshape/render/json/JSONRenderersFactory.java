package com.redshape.render.json;

import com.redshape.render.*;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

public class JSONRenderersFactory extends AbstractRenderersFactory {
    private static final Logger log = Logger.getLogger( JSONRenderersFactory.class );

    public JSONRenderersFactory() {
        super( JSONRenderersFactory.class );
    }

    public String getFactoryId() {
        return "json";
    }

	
}
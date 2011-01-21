package com.redshape.renderer.json;

import com.redshape.renderer.AbstractRenderersFactory;
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
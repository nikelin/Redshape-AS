package com.redshape.io.protocols.core.hydrators;

import org.apache.log4j.Logger;

import com.redshape.io.net.request.RequestException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:24:22 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRequestHydrator implements RequestHydrator {
    private static final Logger log = Logger.getLogger( AbstractRequestHydrator.class );
    protected String data;

    public AbstractRequestHydrator( String data ) throws RequestException {
        this.data = data;

        this.parse(data);
    }
}
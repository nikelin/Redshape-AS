package com.redshape.io.protocols.http;

import com.redshape.io.protocols.core.AbstractVersionsRegistry;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 11:06:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpVersionsRegistry extends AbstractVersionsRegistry<IHttpProtocol<?, ?, ?, ?>, HttpProtocolVersion> {

    @Override
    public boolean isSupports( HttpProtocolVersion version ) {
        try {
            return this.getByVersion(version) != null;
        } catch ( InstantiationException e ) {
            return false;
        }
    }

    @Override
    public HttpProtocolVersion getLastVersion() {
        return HttpProtocolVersion.LAST_VERSION;
    }

    @Override
    public HttpProtocolVersion getVersion( String name ) {
        return HttpProtocolVersion.valueOf(name);
    }

}

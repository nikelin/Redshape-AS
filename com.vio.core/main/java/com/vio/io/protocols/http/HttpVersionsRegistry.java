package com.vio.io.protocols.http;

import com.vio.io.protocols.core.AbstractVersionsRegistry;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.IProtocolVersion;
import com.vio.io.protocols.core.IVersionsRegistry;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 11:06:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpVersionsRegistry extends AbstractVersionsRegistry<IHttpProtocol, HttpProtocolVersion> {

    public boolean isSupports( HttpProtocolVersion version ) {
        try {
            return this.getByVersion(version) != null;
        } catch ( InstantiationException e ) {
            return false;
        }
    }

    public HttpProtocolVersion getLastVersion() {
        return HttpProtocolVersion.LAST_VERSION;
    }

    public HttpProtocolVersion getVersion( String name ) {
        return HttpProtocolVersion.valueOf(name);
    }

}

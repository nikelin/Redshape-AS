package com.vio.io.protocols.core;

import com.vio.io.protocols.http.HttpProtocolVersion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 11:46:01 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractVersionsRegistry<P extends IProtocol, V extends IProtocolVersion> implements IVersionsRegistry<P, V> {
    private Map<V, P> instances = new HashMap();

    public P addVersion( V version, P protocol ) {
        return this.instances.put( version, protocol );
    }

    protected P newInstance( Class<P> protocolClass )
        throws InstantiationException {
        P protocol;
        try {
            protocol = protocolClass.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        return protocol;
    }

    public V getProtocolVersion( P protocol ) {
        return this.getProtocolVersion( (Class<P>) protocol.getClass() );
    }

    public V getProtocolVersion( Class<P> protocol ) {
        for ( V version : instances.keySet() ) {
            if ( protocol.equals( instances.get(version).getClass() ) ) {
                return version;
            }
        }

        return null;
    }

    public P getByVersion( String version )
        throws InstantiationException {
        return this.getByVersion( this.getVersion(version) );
    }

    public P getByVersion( V version )
        throws InstantiationException {
        return instances.get( version );
    }

    public P getActualProtocol() throws InstantiationException {
        return this.getByVersion( this.getLastVersion() );
    }

}

package com.redshape.io.protocols.core;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 10:42:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IVersionsRegistry<P extends IProtocol, V extends IProtocolVersion> {
    
    public V getVersion( String versionName );
    
    public V getProtocolVersion( P protocol );

    public V getProtocolVersion( Class<P> protocol );

    public P addVersion( V version, P protocol);

    public P getByVersion( V version ) throws InstantiationException;

    public P getByVersion( String version ) throws InstantiationException;

    public V getLastVersion();

    public boolean isSupports( V version );

    public P getActualProtocol() throws InstantiationException;
    
}

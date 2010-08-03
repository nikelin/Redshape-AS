package com.vio.io.protocols.vanilla;

import com.vio.io.protocols.core.AbstractVersionsRegistry;
import com.vio.io.protocols.http.HttpProtocolVersion;
import com.vio.io.protocols.http.IHttpProtocol;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 11:06:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class VanillaVersionsRegistry extends AbstractVersionsRegistry<IVanillaProtocol, VanillaProtocolVersion> {
    private static final Logger log = Logger.getLogger( VanillaVersionsRegistry.class );

    public boolean isSupports( VanillaProtocolVersion version ) {
        try {
            return this.getByVersion(version) != null;
        } catch ( InstantiationException e ) {
            return false;
        }
    }

    public VanillaProtocolVersion getLastVersion() {
        return VanillaProtocolVersion.LAST_VERSION;
    }

    public VanillaProtocolVersion getVersion( String name ) {
        log.info( name );
        log.info( VanillaProtocolVersion.valueOf(name) );
        
        return VanillaProtocolVersion.valueOf(name);
    }

}
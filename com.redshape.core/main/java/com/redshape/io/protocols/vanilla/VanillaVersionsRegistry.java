package com.redshape.io.protocols.vanilla;

import com.redshape.io.protocols.core.AbstractVersionsRegistry;
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

    @Override
    public boolean isSupports( VanillaProtocolVersion version ) {
        try {
            return this.getByVersion(version) != null;
        } catch ( InstantiationException e ) {
            return false;
        }
    }

    @Override
    public VanillaProtocolVersion getLastVersion() {
        return VanillaProtocolVersion.LAST_VERSION;
    }

    @Override
    public VanillaProtocolVersion getVersion( String name ) {
        return VanillaProtocolVersion.valueOf(name);
    }

}
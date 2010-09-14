package com.redshape.io.protocols.vanilla;

import com.redshape.io.protocols.core.IProtocolVersion;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 4:41:22 PM
 * To change this template use File | Settings | File Templates.
 */
public final class VanillaProtocolVersion implements IProtocolVersion {
    public static final VanillaProtocolVersion VERSION_1 = new VanillaProtocolVersion("VERSION_1");
    public static final VanillaProtocolVersion LAST_VERSION = VERSION_1;

    private static final VanillaProtocolVersion[] SUPPORTED = new VanillaProtocolVersion[] { VERSION_1 };

    private String version;

    private VanillaProtocolVersion( String version ) {
        this.version = version;
    }

    public static VanillaProtocolVersion valueOf( String value ) {
        for ( VanillaProtocolVersion supported : SUPPORTED ) {
            if ( supported.equals(value) ) {
                return supported;
            }
        }

        return null;
    }

    public String name() {
        return this.version;
    }

    public boolean equals( String version ) {
        return this.toString().equals( version );
    }

    public String toString() {
        return this.name();
    }
}

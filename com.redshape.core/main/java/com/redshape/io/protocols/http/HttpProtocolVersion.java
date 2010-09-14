package com.redshape.io.protocols.http;

import com.redshape.io.protocols.core.IProtocolVersion;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 4:41:22 PM
 * To change this template use File | Settings | File Templates.
 */
public final class HttpProtocolVersion implements IProtocolVersion {
    public static final HttpProtocolVersion HTTP_10 = new HttpProtocolVersion("HTTP/1.0");
    public static final HttpProtocolVersion HTTP_11 = new HttpProtocolVersion("HTTP/1.1");
    public static final HttpProtocolVersion LAST_VERSION = HTTP_11;

    private static final HttpProtocolVersion[] SUPPORTED = new HttpProtocolVersion[] { HTTP_10, HTTP_11 };

    private String version;

    private HttpProtocolVersion( String version ) {
        this.version = version;
    }

    public static HttpProtocolVersion valueOf( String value ) {
        for ( HttpProtocolVersion supported : SUPPORTED ) {
            if ( supported.version.equals(value) ) {
                return supported;
            }
        }

        return null;
    }

    public String name() {
        return this.version;
    }

    public boolean equals( String version ) {
        return this.name().equals( version );
    }

    public String toString() {
        return this.name();
    }
}

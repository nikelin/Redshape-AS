package com.redshape.io.protocols.core;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 10:35:25 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocolVersion {

    public String name();

    public boolean equals( String version );

}

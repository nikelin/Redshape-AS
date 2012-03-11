package com.redshape.net.j2ee;

/**
 * @author nikelin
 * @date 14:26
 */
public class ServerType extends com.redshape.net.ServerType {

    protected ServerType(String code) {
        super(code);
    }

    public static final ServerType J2EE = new ServerType("Server.Type.J2EE");
}

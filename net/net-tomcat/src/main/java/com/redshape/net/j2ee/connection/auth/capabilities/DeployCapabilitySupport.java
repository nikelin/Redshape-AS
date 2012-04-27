package com.redshape.net.j2ee.connection.auth.capabilities;

import com.redshape.net.connection.capabilities.IServerCapabilitySupport;
import com.redshape.net.j2ee.connection.TomcatConnectionSupport;

/**
 * @author nikelin
 * @date 21:40
 */
public class DeployCapabilitySupport implements IServerCapabilitySupport {
    private TomcatConnectionSupport connection;

    public DeployCapabilitySupport( TomcatConnectionSupport connection) {
        this.connection = connection;
    }

    protected TomcatConnectionSupport getConnection() {
        return this.connection;
    }

    public void deploy() {

    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}

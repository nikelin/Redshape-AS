package com.redshape.net.j2ee.connection.auth.capabilities;

import com.redshape.net.connection.capabilities.IServerCapabilitySupport;
import com.redshape.net.j2ee.connection.TomcatConnectionSupport;

import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.factories.DeploymentFactoryManager;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;
import java.io.File;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

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

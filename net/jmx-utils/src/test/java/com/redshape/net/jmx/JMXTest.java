package com.redshape.net.jmx;

import com.redshape.utils.tests.AbstractContextAwareTest;
import org.junit.Assert;
import org.junit.Test;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.shared.factories.DeploymentFactoryManager;
import javax.enterprise.deploy.spi.DeploymentManager;

import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/16/12
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXTest extends AbstractContextAwareTest<String> {

    private JMXFactory factory;

    public JMXTest() {
        super("src/test/resources/context.xml");
    }

    protected JMXFactory getFactory() {
        return this.getContext().getBean(JMXFactory.class);
    }

    protected JMXUtil getJMXUtil() {
        return this.getContext().getBean(JMXUtil.class);
    }

    @Test
    public void testMain() {
        try {
            DeploymentFactoryManager dfm = DeploymentFactoryManager.getInstance();
            for ( DeploymentFactory df : dfm.getDeploymentFactories() ) {
                System.out.println(df.getClass().getCanonicalName());
            }

            DeploymentFactory df = new org.glassfish.deployapi.SunDeploymentFactory();
            dfm.registerDeploymentFactory(df);

            DeploymentManager manager = dfm.getDeploymentManager("deployer:Sun:AppServer::0.0.0.0:4848:http", "admin", "");

            System.err.println("Getting targets");
            Target[] targets = manager.getTargets();


        } catch ( Throwable e ) {
            Assert.fail(e.getMessage());
        }
    }

}

package com.redshape.net.jmx;

import com.redshape.utils.Commons;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.StringUtils;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;



/**
 * @author nikelin
 * @date 20:06
 */
public class JMXAgent {
    private JMXConnectorServer cs;
    private MBeanServer mbs;
    private IResourcesLoader resourcesLoader;

    private boolean enableRmiAdapter;
    private boolean startRegistry;
    private boolean enableSsl;
    private boolean enableMinaMonitor;

    private String rmiAdapterPort = "8888";
    private String rmiAdapterRemotePort = "";
    private String rmiAdapterHost = "localhost";
    private String rmiAdapterPath = "/ras";
    
    private String domain;
    
    private String remotePassword;
    private String remoteUser;

    private String remotePasswordProperties;
    private String remoteAccessProperties;

    private String remoteSSLKeystore;
    private String remoteSSLKeystorePass;

    public JMXAgent(MBeanServer mbeanServer, String domain, IResourcesLoader resourcesLoader)
            throws JMXException {
        Commons.checkNotNull(mbeanServer);
        Commons.checkNotNull(resourcesLoader);
        Commons.checkNotNull(domain);

        this.domain = domain;
        this.mbs = mbeanServer;
        this.resourcesLoader = resourcesLoader;
    }

    public String getDomain() {
        return domain;
    }

    protected MBeanServer getMbs() {
        return mbs;
    }

    protected IResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    protected JMXConnectorServer getCs() {
        return cs;
    }

    public boolean registerMBean(Object instance, String className, Class interfaceClass)
            throws JMXException {
        try {
            this.getMbs().registerMBean(
                    new StandardMBean(instance, interfaceClass),
                    new ObjectName( this.getDomain() + ":type="
                            + StringUtils.preparePathByClass(className) )
            );

            return true;
        } catch (InstanceAlreadyExistsException e) {
            throw new JMXException( e.getMessage(), e );
        } catch ( NotCompliantMBeanException e ) {
            throw new JMXException( e.getMessage(), e );
        } catch ( MalformedObjectNameException e ) {
            throw new JMXException( e.getMessage(), e );
        } catch ( MBeanException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public <T> T invoke( String className, String operationName,
                           Object[] params, String[] signature ) throws JMXException {
        try {
            return (T) this.getMbs().invoke(
                    new ObjectName(StringUtils.preparePathByClass(className)),
                    operationName, params, signature);
        } catch ( Exception e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public boolean registerMBean(Object instance,
                                 String className,
                                 Class interfaceClass,
                                 ObjectName name)
            throws JMXException {
        try {
            this.getMbs().registerMBean(new StandardMBean(instance, interfaceClass), name);
            return true;
        } catch (InstanceAlreadyExistsException e) {
            throw new JMXException( e.getMessage(), e );
        } catch ( NotCompliantMBeanException e ) {
            throw new JMXException( e.getMessage(), e );
        } catch ( MBeanRegistrationException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public boolean registerMBean(Object instance, String className,
                                 Class interfaceClass, String name)
            throws JMXException {
        try {
            String cName = className;
            if (cName.indexOf('.') != -1) {
                cName = cName.substring(cName.lastIndexOf('.')).replaceFirst("[\\.]", "");
            }

            this.getMbs().registerMBean(new StandardMBean(instance, interfaceClass),
                    new ObjectName( this.getDomain() + ":type=" + cName +
                            ",name=" + name));

            return true;
        } catch (InstanceAlreadyExistsException e) {
            throw new JMXException( e.getMessage(), e );
        } catch ( MalformedObjectNameException e ) {
            throw new JMXException( e.getMessage(), e );
        } catch ( MBeanRegistrationException e ) {
            throw new JMXException( e.getMessage(), e );
        } catch ( NotCompliantMBeanException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public void shutdown() throws JMXException {
        try {
            if (null != cs) {
                cs.stop();
            }

            String domain = this.getDomain();
            for (ObjectName oname : this.getMbs().queryNames(new ObjectName(domain + ":*"), null)) {
                if (domain.equals(oname.getDomain())) {
                    unregisterMBean(oname);
                }
            }
        } catch ( IOException e ) {
            throw new JMXException( "I/O related exception", e );
        } catch ( MalformedObjectNameException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }


    /**
     * @param oName bean instance
     * @return
     */
    public boolean unregisterMBean(ObjectName oName) throws JMXException {
        try {
            if (null != oName) {
                if (this.getMbs().isRegistered(oName)) {
                    this.getMbs().unregisterMBean(oName);
                    return true;
                }
            }

            return false;
        } catch ( InstanceNotFoundException e ) {
            throw new JMXException( e.getMessage(), e );
        } catch ( MBeanRegistrationException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }


    /**
     * @param oName object name
     * @param key key
     * @param value new value
     * @return
     */
    public boolean updateMBeanAttribute(ObjectName oName, String key, int value)
            throws JMXException {
        try {
            if (null != oName) {
                if (this.getMbs().isRegistered(oName)) {
                    this.getMbs().setAttribute(oName, new javax.management.Attribute("key", value));
                    return true;
                }
            }

            return false;
        } catch ( Exception e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }


    /**
     * @param oName object name
     * @param key key
     * @param value new value
     * @return
     */
    public boolean updateMBeanAttribute(ObjectName oName, String key, String value)
            throws JMXException {
        try {
            if (null != oName) {
                if (this.getMbs().isRegistered(oName)) {
                    this.getMbs().setAttribute(oName, new javax.management.Attribute("key", value));
                    return true;
                }
            }

            return false;
        } catch ( Exception e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    protected Map<String, Object> buildEnvironment() throws JMXException {
        try {
            Map<String, Object> env = new HashMap<String, Object>();
            System.setProperty("java.rmi.server.hostname", rmiAdapterHost);

            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(Integer.valueOf(rmiAdapterPort));
                for (String regName : registry.list()) {
                    if (regName.equals(rmiAdapterPath )) {
                        registry.unbind("red5");
                    }
                }
            } catch (RemoteException re) {
                if (startRegistry) {
                    registry = LocateRegistry.createRegistry(Integer.valueOf(rmiAdapterPort));
                }
            }

            if (enableSsl) {
                System.setProperty("javax.net.ssl.keyStore", remoteSSLKeystore);
                System.setProperty("javax.net.ssl.keyStorePassword", remoteSSLKeystorePass);

                SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
                SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
                env.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE, csf);
                env.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE, ssf);
            }

            if ( !remoteAccessProperties.trim().isEmpty() ) {
                env.put("jmx.remote.x.access.file", remoteAccessProperties.replace("file:", ""));
                env.put("jmx.remote.x.password.file", remotePasswordProperties.replace("file:", ""));
            } else if ( !remoteUser.trim().isEmpty() ) {
                String[] creds = new String[2];
                creds[0] = this.getRemoteUser();
                creds[1] = this.getRemotePassword();

                env.put(JMXConnector.CREDENTIALS, creds);
            }

            return env;
        } catch (ConnectException e) {
            throw new JMXException("Could not establish RMI connection!", e );
        } catch (IOException e) {
            throw new JMXException("I/O related exception", e );
        } catch ( NotBoundException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    protected JMXServiceURL buildConnectionURL() throws JMXException {
        try {
            JMXServiceURL url;
            if (!rmiAdapterPort.trim().isEmpty()) {
                url = new JMXServiceURL("service:jmx:rmi://" + rmiAdapterHost + ":"
                        + rmiAdapterRemotePort +
                        "/jndi/rmi://" + rmiAdapterHost + ":" + rmiAdapterPort + "/" + rmiAdapterPath );
            } else {
                url = new JMXServiceURL("service:jmx:rmi://" + rmiAdapterHost + ":"
                        + rmiAdapterPort + "/jndi/rmi://" + rmiAdapterHost + ":"
                        + rmiAdapterPort + rmiAdapterPath );
            }
            
            return url;
        } catch ( MalformedURLException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public void start() throws JMXException {
        try {
            if (!enableRmiAdapter) {
                return;
            }
            cs = JMXConnectorServerFactory.newJMXConnectorServer(
                    this.buildConnectionURL(),
                    this.buildEnvironment(),
                    this.getMbs());

            cs.start();
        } catch ( IOException e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public boolean isEnableRmiAdapter() {
        return enableRmiAdapter;
    }

    public void setEnableRmiAdapter(boolean enableRmiAdapter) {
        this.enableRmiAdapter = enableRmiAdapter;
    }

    public boolean isStartRegistry() {
        return startRegistry;
    }

    public void setStartRegistry(boolean startRegistry) {
        this.startRegistry = startRegistry;
    }

    public boolean isEnableSsl() {
        return enableSsl;
    }

    public void setEnableSsl(boolean enableSsl) {
        this.enableSsl = enableSsl;
    }

    public boolean isEnableMinaMonitor() {
        return enableMinaMonitor;
    }

    public void setEnableMinaMonitor(boolean enableMinaMonitor) {
        this.enableMinaMonitor = enableMinaMonitor;
    }

    public String getRmiAdapterPort() {
        return rmiAdapterPort;
    }

    public void setRmiAdapterPort(String rmiAdapterPort) {
        this.rmiAdapterPort = rmiAdapterPort;
    }

    public String getRmiAdapterRemotePort() {
        return rmiAdapterRemotePort;
    }

    public void setRmiAdapterRemotePort(String rmiAdapterRemotePort) {
        this.rmiAdapterRemotePort = rmiAdapterRemotePort;
    }

    public String getRmiAdapterHost() {
        return rmiAdapterHost;
    }

    public void setRmiAdapterHost(String rmiAdapterHost) {
        this.rmiAdapterHost = rmiAdapterHost;
    }

    public String getRmiAdapterPath() {
        return rmiAdapterPath;
    }

    public void setRmiAdapterPath(String rmiAdapterPath) {
        this.rmiAdapterPath = rmiAdapterPath;
    }

    public String getRemotePasswordProperties() {
        return remotePasswordProperties;
    }

    public void setRemotePasswordProperties(String remotePasswordProperties) {
        this.remotePasswordProperties = remotePasswordProperties;
    }

    public String getRemoteAccessProperties() {
        return remoteAccessProperties;
    }

    public void setRemoteAccessProperties(String remoteAccessProperties) {
        this.remoteAccessProperties = remoteAccessProperties;
    }

    public String getRemoteSSLKeystore() {
        return remoteSSLKeystore;
    }

    public void setRemoteSSLKeystore(String remoteSSLKeystore) {
        this.remoteSSLKeystore = remoteSSLKeystore;
    }

    public String getRemoteSSLKeystorePass() {
        return remoteSSLKeystorePass;
    }

    public void setRemoteSSLKeystorePass(String remoteSSLKeystorePass) {
        this.remoteSSLKeystorePass = remoteSSLKeystorePass;
    }
}
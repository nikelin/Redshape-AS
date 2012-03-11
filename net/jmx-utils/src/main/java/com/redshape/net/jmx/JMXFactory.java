package com.redshape.net.jmx;

/**
 * @author nikelin
 * @date 20:17
 */

import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.StringUtils;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;

/**
 * @author nikelin
 * @date 20:17
 */
@SuppressWarnings("cast")
public class JMXFactory {
    private String domain = "com.redshape.net.jmx";
    private IResourcesLoader resourcesLoader;
    private MBeanServer mbs;

    protected IResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    protected MBeanServer getMBeanServer() {
        if ( mbs == null ) {
            try {
                mbs = MBeanServerFactory.findMBeanServer(null).get(0);
            } catch (Exception e) {
                // grab a reference to the "platform" MBeanServer
                mbs = ManagementFactory.getPlatformMBeanServer();
            }
        }

        return mbs;
    }

    public ObjectName createMBean(String className, String attributes) throws JMXException {
        try {
            StringBuilder objectNameStr = new StringBuilder(this.getDomain());
            objectNameStr.append(":type=");
            objectNameStr.append(className.substring(className.lastIndexOf(".") + 1));
            objectNameStr.append(',');
            objectNameStr.append(attributes);

            ObjectName objectName = new ObjectName(objectNameStr.toString());
            if ( !this.getMBeanServer().isRegistered(objectName) ) {
                this.getMBeanServer().createMBean(className, objectName);
            }

            return objectName;
        } catch ( Exception e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public ObjectName createObjectName(String... strings) throws JMXException {
        StringBuilder sb = new StringBuilder( this.getDomain() );
        sb.append(':');
        for (int i = 0, j = 1; i < strings.length; i += 2, j += 2) {
            sb.append(strings[i]);
            sb.append('=');
            sb.append(strings[j]);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);

        ObjectName objName;
        try {
            objName = new ObjectName(sb.toString());
        } catch (Exception e) {
            throw new JMXException( e.getMessage(), e );
        }

        return objName;
    }


    public ObjectName createSimpleMBean(String className, String objectNameStr) throws JMXException {
        try {
            ObjectName objectName = ObjectName.getInstance(objectNameStr);
            if (!this.getMBeanServer().isRegistered(objectName)) {
                this.getMBeanServer().createMBean(className, objectName);
            }

            return objectName;
        } catch ( Exception e ) {
            throw new JMXException( e.getMessage(), e );
        }
    }

    public boolean registerNewMBean(String className, Class interfaceClass)
            throws JMXException {
        try {
            this.getMBeanServer().registerMBean(
                new StandardMBean(Class.forName(className).newInstance(), interfaceClass),
                new ObjectName(this.getDomain() + ":type=" + StringUtils.preparePathByClass(className) )
            );

            return true;
        } catch (Exception e) {
            throw new JMXException("Could not register the " + className + " MBean", e);
        }
    }

    public boolean registerNewMBean(String className, Class interfaceClass, ObjectName name)
        throws JMXException {
        try {
            this.getMBeanServer().registerMBean(
                new StandardMBean(Class.forName(className).newInstance(), interfaceClass),
                name
            );

            return true;
        } catch (Exception e) {
            throw new JMXException("Could not register the " + className + " MBean", e);
        }
    }

    public boolean registerNewMBean(String className, Class interfaceClass, String name)
        throws JMXException {
        try {
            this.getMBeanServer().registerMBean(
                new StandardMBean(Class.forName(className).newInstance(), interfaceClass),
                new ObjectName(this.getDomain()+ ":type=" + StringUtils.preparePathByClass(className)
                        + ",name=" + name)
            );
            
            return true;
        } catch (Exception e) {
            throw new JMXException( e.getMessage(), e );
        }
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public JMXAgent createAgent() throws JMXException {
        return new JMXAgent( this.getMBeanServer(),
                this.getDomain(),
                this.getResourcesLoader() );
    }

}

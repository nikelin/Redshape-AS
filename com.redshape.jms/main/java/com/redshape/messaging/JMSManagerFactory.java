package com.redshape.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.HashMap;
import java.util.Map;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date Apr 6, 2010
 */
public class JMSManagerFactory {
    private static Class<? extends JMSManager> defaultManager = AMQManager.class;
    private static JMSManagerFactory defaultInstance = new JMSManagerFactory();
    private Map<Class<? extends JMSManager>, JMSManager> managers = new HashMap<Class<? extends JMSManager>, JMSManager>();

    public static Integer MESSAGES_RECEIVE_DELAY = 3000;
    public static Integer MESSAGES_RECEIVE_PERIOD = 3000;
    public static Destination DEFAULT_MANAGER_DESTINATION  = JMSManager.API_SERVER_DESTINATION;

    private JMSManagerFactory() {}

    public static JMSManagerFactory getDefault() {
        return defaultInstance;
    }

    public JMSManager getManager() throws JMSException {
        try {
            return this.getManager( defaultManager );
        } catch ( Throwable e ) {
            throw new JMSException("Cannot retrieve default manager instance.");
        }
    }

    public JMSManager getManager( String className ) throws JMSException {
        try {
            return this.getManager( (Class<? extends JMSManager>) Class.forName( className ) );
        } catch ( Throwable e ) {
            throw new JMSException("Cannot retrieve requested manager instance.");
        }
    }

    public JMSManager getManager( Class<? extends JMSManager> clazz ) throws InstantiationException {
        JMSManager manager = this.managers.get( clazz );
        if ( manager != null ) {
            return manager;
        }

        manager = this.createManager(clazz);

        this.managers.put( clazz, manager );

        return manager;
    }

    protected JMSManager createManager( Class<? extends JMSManager> clazz ) throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public void setDefault( JMSManagerFactory defaultEntity ) {
        defaultInstance = defaultEntity;
    }

    public void setDefaultManager( Class<? extends JMSManager> defaultEntity ) {
        defaultManager = defaultEntity;
    }
}

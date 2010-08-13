package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.config.readers.ConfigReaderException;
import com.vio.notifications.transport.ITransport;
import com.vio.notifications.transport.configuration.ConfigurationException;
import com.vio.notifications.transport.configuration.ITransportConfiguration;
import com.vio.notifications.transport.TransportException;
import com.vio.notifications.transport.TransportsFactory;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:49:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitTransport extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( InitTransport.class );

    @Override
    public void process() throws BootstrapException {
        try {
            List<String> transportClasses = Registry.getServerConfig().getTransportersClasses();
            for ( String transportClass : transportClasses ) {
                this.initializeTransporter( transportClass );
            }
        } catch ( ConfigReaderException e ) {
            log.error("Cannot read data from config!", e );
            throw new BootstrapException();
        }
    }

    private void initializeTransporter( String clazz ) throws BootstrapException {
        try {
            Class<? extends ITransport> transportClass = (Class<? extends ITransport>) Class.forName( clazz );

            ITransport transportInstance = transportClass.newInstance();
            transportInstance.setConfiguration( this.buildConfiguration( transportInstance ) );

            TransportsFactory.getDefault().registerTransport( transportInstance );
        } catch ( Throwable e ) {
            log.error( "Cannot initialize transporter class: " + clazz, e );
            throw new BootstrapException();
        }
    }

    private ITransportConfiguration buildConfiguration( ITransport transport ) throws ConfigurationException, TransportException, ConfigReaderException {
        ITransportConfiguration config = transport.createConfig();

        Map<String, String> properties = Registry.getServerConfig().getTransporterProperties( transport.getClass().getCanonicalName() );
        for ( String name  : properties.keySet() ) {
            config.setProperty( name, properties.get(name) );
        }

        return config;
    }

}

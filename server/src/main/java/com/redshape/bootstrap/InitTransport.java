package com.redshape.bootstrap;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.notifications.transport.ITransport;
import com.redshape.notifications.transport.configuration.ConfigurationException;
import com.redshape.notifications.transport.configuration.ITransportConfiguration;
import com.redshape.notifications.transport.TransportException;
import com.redshape.notifications.transport.TransportsFactory;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:49:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class InitTransport extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( InitTransport.class );

    @Override
    public void process() throws BootstrapException {
        try {
            IConfig transportsListNode = this.getConfig().get("settings").get("transports");
            if ( !transportsListNode.isNull() && transportsListNode.hasChilds() ) {
                for ( IConfig transportConfigNode : transportsListNode.childs() ) {
                    this.initializeTransporter( transportConfigNode.attribute("class") );
                }
            }
        } catch ( ConfigException e ) {
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

    private ITransportConfiguration buildConfiguration( ITransport transport ) throws ConfigurationException, TransportException, ConfigException {
        ITransportConfiguration config = transport.createConfig();

        for( IConfig transportConfigNode : this.getConfig().get("settings").get("transports").childs() ) {
            if ( transportConfigNode.attribute("class").equals( transport.getClass().getCanonicalName() ) ) {
                for ( IConfig propertyNode : transportConfigNode.get("properties").childs() ) {
                    config.setProperty( propertyNode.attribute("name"), propertyNode.attribute("value") );
                }
            }
        }

        return config;
    }

}

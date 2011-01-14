package com.redshape.notifications.transport;

import com.redshape.notifications.transport.annotations.Transporter;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 2:57:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransportsFactory implements ITransportsFactory {
    private static final Logger log = Logger.getLogger( TransportsFactory.class );
    private static ITransportsFactory defaultInstance = new TransportsFactory();
    private Map<String, ITransport> transporters = new HashMap();

    public static ITransportsFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( ITransportsFactory instance ) {
        defaultInstance = instance;
    }

    public ITransport getTransport( String id ) throws InstantiationException {
        return this.transporters.get(id);
    }

    public ITransport getTransport( Class<? extends ITransport> clazz ) throws InstantiationException {
        Transporter annotation = clazz.getAnnotation(Transporter.class);
        if ( annotation == null ) {
            return null;
        }

        ITransport transporter = this.transporters.get(annotation.name());
        if ( transporter == null ) {
            return null;
        }

        return transporter;
    }

    public void registerTransport( ITransport transport ) throws TransportException {
        Transporter transporter = transport.getClass().getAnnotation( Transporter.class );
        if ( transporter == null ) {
            throw new TransportException("Transport does not provides ID annotation.");
        }

        this.registerTransport( transport, transporter.name() );
    }

    public void registerTransport( ITransport transport, String name ) {
        this.transporters.put( name, transport );
    }

}

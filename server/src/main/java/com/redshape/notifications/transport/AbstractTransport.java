package com.redshape.notifications.transport;

import com.redshape.notifications.transport.annotations.Transporter;
import com.redshape.notifications.transport.configuration.ITransportConfiguration;
import com.redshape.notifications.transport.destination.IDestination;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:17:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTransport<
                        C extends ITransportConfiguration,
                        D extends IDestination>
            implements ITransport<C, D> {
    private C configuration;

    public String getId() {
        Transporter annotation = this.getClass().getAnnotation(Transporter.class);
        if ( annotation != null ) {
            return annotation.name();
        }

        return null;
    }

    public void setConfiguration( C configuration ) {
        this.configuration = configuration;
    }

    public C getConfiguration() {
        return this.configuration;
    }
 
}

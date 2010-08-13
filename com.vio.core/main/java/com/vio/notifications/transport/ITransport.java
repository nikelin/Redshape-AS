package com.vio.notifications.transport;

import com.vio.notifications.INotification;
import com.vio.notifications.transport.configuration.ITransportConfiguration;
import com.vio.notifications.transport.destination.IDestination;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 2:54:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITransport<C extends ITransportConfiguration, D extends IDestination> {

    public String getId();

    public void send( INotification notification, D from, D to ) throws TransportException;

    public void setConfiguration( C configuration );

    public C getConfiguration();

    public C createConfig() throws TransportException;

    public D createDestination() throws TransportException;

}

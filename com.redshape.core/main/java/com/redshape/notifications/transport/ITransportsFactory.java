package com.redshape.notifications.transport;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:03:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITransportsFactory {

    /**
     * Get transport by it's related ID ( @see com.redshape.notifications.transport.annotations.Transporter )
     * @param id
     * @return
     * @throws InstantiationException
     */
    public ITransport getTransport( String id ) throws InstantiationException;

    public ITransport getTransport( Class<? extends ITransport> clazz ) throws InstantiationException;

    public void registerTransport( ITransport transport ) throws TransportException;

    public void registerTransport( ITransport transport, String name ) throws TransportException;

}

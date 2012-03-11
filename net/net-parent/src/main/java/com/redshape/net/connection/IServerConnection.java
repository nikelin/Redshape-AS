package com.redshape.net.connection;

import com.redshape.utils.IParametrized;

/**
 * @author nikelin
 * @date 12:55
 */
public interface IServerConnection extends IParametrized<ConnectionAttribute> {

    public boolean isConnected() throws ConnectionException;

    public void connect() throws ConnectionException;

    public void disconnect() throws ConnectionException;

}

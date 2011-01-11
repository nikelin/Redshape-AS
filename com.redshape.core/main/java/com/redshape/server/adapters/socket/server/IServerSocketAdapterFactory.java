package com.redshape.server.adapters.socket.server;

import com.redshape.server.ServerException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 2:07:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IServerSocketAdapterFactory {

    public IServerSocketAdapter createServerSocketAdapter( String host, int port ) throws IOException, ServerException;

    public IServerSocketAdapter createSSLServerSocketAdapter( String host, int port ) throws IOException, ServerException;

}

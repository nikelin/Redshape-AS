package com.redshape.net.connection.auth;

import com.redshape.net.IServer;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/21/12
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConnectionAuthenticatorsProvider {

    public <T> IConnectionAuthenticator<T> provide( IServer server );

}

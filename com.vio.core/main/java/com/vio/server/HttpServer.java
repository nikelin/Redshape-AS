package com.vio.server;

import com.vio.io.protocols.response.IResponse;
import com.vio.io.protocols.response.Response;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 1:56:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpServer extends AbstractServer<IResponse> {

    public boolean isPropertySupports( String name ) {
        return true;
    }

    public void startListen() {

    }

    public void shutdown() {

    }

    public Response createResponseObject() {
        return new Response();
    }

}

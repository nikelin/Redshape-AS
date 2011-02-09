package com.redshape.io.protocols.http.impl;

import com.redshape.io.protocols.core.Constants;
import com.redshape.io.protocols.core.IProtocolVersion;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.http.AbstractHttpProtocol;
import com.redshape.io.protocols.http.HttpProtocolVersion;
import com.redshape.io.protocols.http.hydrators.HttpRequestHydrator;
import com.redshape.io.protocols.http.readers.HttpRequestReader;
import com.redshape.io.protocols.http.renderers.HttpResponseRenderer;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.http.response.IHttpResponse;
import com.redshape.io.protocols.core.writers.ResponseWriter;
import com.redshape.io.protocols.dispatchers.IHttpDispatcher;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 7:14:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpProtocol_11 extends AbstractHttpProtocol<IHttpRequest, IHttpDispatcher, IHttpResponse, BufferedInput> {

    public HttpProtocol_11() {
        super( HttpProtocol_11.class );

        this.setReader( new HttpRequestReader( new HttpRequestHydrator() ) );
        this.setWriter( new ResponseWriter( new HttpResponseRenderer() ) );
    }

    @Override
    public IProtocolVersion getProtocolVersion() {
        return HttpProtocolVersion.HTTP_11;
    }

    @Override
    public String getConstant( Constants id ) {
        switch ( id ) {
            case API_KEY_HEADER:
                return "Api-Key";
            case PROTOCOL_VERSION_HEADER:
            default:
                return null;
        }
    }
}

package com.vio.io.protocols.http.impl;

import com.vio.io.protocols.core.Constants;
import com.vio.io.protocols.core.AbstractProtocol;
import com.vio.io.protocols.core.IProtocolVersion;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.http.HttpProtocolVersion;
import com.vio.io.protocols.http.IHttpProtocol;
import com.vio.io.protocols.http.hydrators.HttpRequestHydrator;
import com.vio.io.protocols.http.readers.HttpRequestReader;
import com.vio.io.protocols.http.renderers.HttpResponseRenderer;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.http.response.IHttpResponse;
import com.vio.io.protocols.core.writers.ResponseWriter;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 7:14:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpProtocol_11 extends AbstractProtocol<IHttpRequest, IHttpResponse, BufferedInput>
                                     implements IHttpProtocol<IHttpRequest, IHttpResponse, BufferedInput> {

    public HttpProtocol_11() {
        super();

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

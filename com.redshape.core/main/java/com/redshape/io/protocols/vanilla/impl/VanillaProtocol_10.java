package com.redshape.io.protocols.vanilla.impl;

import com.redshape.api.dispatchers.vanilla.IVanillaDispatcher;
import com.redshape.io.protocols.core.Constants;
import com.redshape.io.protocols.core.IProtocolVersion;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.vanilla.AbstractVanillaProtocol;
import com.redshape.io.protocols.vanilla.VanillaProtocolVersion;
import com.redshape.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.redshape.io.protocols.vanilla.readers.APIRequestReader;
import com.redshape.io.protocols.vanilla.renderers.JSONResponseRenderer;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.io.protocols.core.writers.ResponseWriter;
import com.redshape.server.processors.request.ApiRequestsProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 7:57:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class VanillaProtocol_10 extends AbstractVanillaProtocol<IApiRequest, IVanillaDispatcher<?, IApiRequest, IApiResponse>, IApiResponse, BufferedInput> {

    public VanillaProtocol_10() {
        super( VanillaProtocol_10.class );

        this.setReader( new APIRequestReader( new JSONRequestHydrator() ) );
        this.setWriter( new ResponseWriter( new JSONResponseRenderer() ) );
    }

    @Override
    public IProtocolVersion getProtocolVersion() {
        return VanillaProtocolVersion.VERSION_1;
    }

    @Override
    public String getConstant( Constants id ) {
        switch( id ) {
            case API_KEY_HEADER:
                return "api_key";
            case PROTOCOL_VERSION_HEADER:
                return "version";
            default:
                return null;
        }
    }

}

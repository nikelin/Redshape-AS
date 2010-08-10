package com.vio.io.protocols.vanilla.impl;

import com.vio.io.protocols.core.Constants;
import com.vio.io.protocols.core.AbstractProtocol;
import com.vio.io.protocols.core.IProtocolVersion;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.vanilla.IVanillaProtocol;
import com.vio.io.protocols.vanilla.VanillaProtocolVersion;
import com.vio.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.vio.io.protocols.vanilla.readers.APIRequestReader;
import com.vio.io.protocols.vanilla.renderers.JSONResponseRenderer;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.io.protocols.core.writers.ResponseWriter;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 7:57:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class VanillaProtocol_10 extends AbstractProtocol<IAPIRequest, IApiResponse, BufferedInput> implements IVanillaProtocol<IAPIRequest, IApiResponse, BufferedInput> {

    public VanillaProtocol_10() {
        super();

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

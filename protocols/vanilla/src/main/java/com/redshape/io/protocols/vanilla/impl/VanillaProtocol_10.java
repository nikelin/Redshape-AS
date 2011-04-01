package com.redshape.io.protocols.vanilla.impl;

import com.redshape.api.requesters.IRequester;

import com.redshape.io.protocols.vanilla.AbstractVanillaProtocol;
import com.redshape.io.protocols.vanilla.VanillaProtocolVersion;
import com.redshape.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.redshape.io.protocols.vanilla.readers.APIRequestReader;
import com.redshape.io.protocols.vanilla.renderers.JSONResponseRenderer;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.io.protocols.core.Constants;
import com.redshape.io.protocols.core.IProtocolVersion;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.writers.IResponseWriter;
import com.redshape.io.protocols.core.writers.ResponseWriter;
import com.redshape.io.protocols.dispatchers.IVanillaDispatcher;
/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 7:57:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class VanillaProtocol_10 extends AbstractVanillaProtocol<IRequester, 
																IApiRequest, 
																IVanillaDispatcher<
																	IRequester, 
																	IApiRequest, 
																	IApiResponse
																>, 
																IApiResponse> {
    public VanillaProtocol_10() {
        super( VanillaProtocol_10.class );

        this.setReader( this.createReader() );
        this.setWriter( this.createWriter() );
    }
    
    protected IResponseWriter<IApiResponse> createWriter() {
    	return new ResponseWriter<IApiResponse>( new JSONResponseRenderer<IApiResponse>() );
    }
    
    protected IRequestReader<IApiRequest> createReader() {
    	return new APIRequestReader( new JSONRequestHydrator() );
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

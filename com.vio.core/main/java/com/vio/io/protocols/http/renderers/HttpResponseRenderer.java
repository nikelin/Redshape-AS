package com.vio.io.protocols.http.renderers;

import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.http.request.HttpCode;
import com.vio.io.protocols.http.response.HttpResponse;
import com.vio.io.protocols.http.response.IHttpResponse;
import com.vio.io.protocols.core.renderers.ResponseRenderer;
import com.vio.io.protocols.core.request.RequestHeader;
import com.vio.io.protocols.core.response.IResponse;
import com.vio.render.RendererException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:36:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponseRenderer implements ResponseRenderer<IHttpResponse> {

    public Object render( ExceptionWithCode e ) throws RendererException {
        throw new RendererException();
    }

    public Object render( Collection<? extends IHttpResponse> e ) throws RendererException {
        throw new RendererException();
    }

    public Object render( IHttpResponse response ) throws RendererException {
         return this.renderSingle(response);
    }

    public byte[] renderBytes( ExceptionWithCode e ) throws RendererException {
        throw new RendererException();
    }

    public byte[] renderBytes( IHttpResponse response ) throws RendererException {
        return this.renderSingle( response ).getBytes();
    }

    public byte[] renderBytes( Collection<? extends IHttpResponse> responses ) throws RendererException {
        throw new RendererException();
    }

    protected String renderSingle( IHttpResponse response ) throws RendererException {
        StringBuilder result = new StringBuilder();

        HttpCode code = response.getCode();
        result.append( code.getCode() )
              .append( HttpResponse.SP )
              .append( code.getReason() )
              .append( HttpResponse.SP )
              .append( response.getProtocolVersion() )
              .append( HttpResponse.CRLF );

        for ( RequestHeader header : response.getHeaders() ) {
            result.append( header.getName() )
                  .append( ":" )
                  .append( HttpResponse.SP )
                  .append( header.getValue() )
                  .append( HttpResponse.CRLF );
        }

        result.append( HttpResponse.CRLF );

        result.append( response.getBody() );

        return result.toString();
    }
}

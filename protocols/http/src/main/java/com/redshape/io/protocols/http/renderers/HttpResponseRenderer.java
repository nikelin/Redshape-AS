package com.redshape.io.protocols.http.renderers;

import com.redshape.io.protocols.http.request.HttpCode;
import com.redshape.io.protocols.http.response.HttpResponse;
import com.redshape.io.protocols.http.response.IHttpResponse;
import com.redshape.io.protocols.core.renderers.IResponseRenderer;
import com.redshape.io.protocols.core.request.RequestHeader;
import com.redshape.renderer.RendererException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:36:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponseRenderer implements IResponseRenderer<String, IHttpResponse> {

    @Override
    public String render( Throwable e ) throws RendererException {
        throw new RendererException();
    }

    @Override
    public String render( Collection<IHttpResponse> e ) throws RendererException {
        throw new RendererException();
    }

    @Override
    public String render( IHttpResponse response ) throws RendererException {
         return this.renderSingle(response);
    }

    @Override
    public byte[] renderBytes( Throwable e ) throws RendererException {
        throw new RendererException();
    }

    @Override
    public byte[] renderBytes( IHttpResponse response ) throws RendererException {
        return this.renderSingle( response ).getBytes();
    }

    @Override
    public byte[] renderBytes( Collection<IHttpResponse> responses ) throws RendererException {
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

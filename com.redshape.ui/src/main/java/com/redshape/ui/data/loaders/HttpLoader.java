package com.redshape.ui.data.loaders;

import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.core.sources.output.BufferedOutput;
import com.redshape.io.protocols.core.writers.IResponseWriter;
import com.redshape.io.protocols.core.writers.ResponseWriter;
import com.redshape.io.protocols.http.hydrators.HttpRequestHydrator;
import com.redshape.io.protocols.http.readers.HttpRequestReader;
import com.redshape.io.protocols.http.renderers.HttpResponseRenderer;
import com.redshape.io.protocols.http.request.HttpCode;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.http.response.HttpResponse;
import com.redshape.ui.events.EventDispatcher;
import com.redshape.ui.data.IModelData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class HttpLoader<V extends IModelData> extends EventDispatcher implements IDataLoader<V> {
    private ThreadLocal<IRequest> lastResponse;

    private HttpRequestReader reader;
    private IResponseWriter writer;
    private HttpConfig config;
    private URLConnection connection;

    public HttpLoader(  HttpConfig config  ) {
        this.reader = this.createLoaderInstance();
        this.writer = this.createWriterInstance();
        this.config = config;
    }

    public void init() throws LoaderException {
        try {
            this.connection = config.getURL().openConnection();
            this.connection.setConnectTimeout( this.config.getTimeout() );
        } catch ( IOException e ) {
            throw new LoaderException();
        }
    }

    public void load() throws LoaderException {
        try {
            this.forwardEvent( LoaderEvents.BeforeLoad, config );

            this.connection.connect();

            BufferedOutput output = new BufferedOutput( this.connection.getOutputStream() );
            BufferedInput input = new BufferedInput( this.connection.getInputStream() );

            HttpResponse response = new HttpResponse();
            response.setCode( HttpCode.OK_200 );

            for ( String param : config.getParameters().keySet() ) {
                response.setParameter( param, config.<String>getParameter(param) );
            }

            this.writer.writeResponse( output, response );

            IRequest request;
            this.lastResponse.set( request = this.reader.readRequest(input) );

            this.forwardEvent( LoaderEvents.Loaded, request );
        } catch ( Throwable e ) {
            this.forwardEvent( LoaderEvents.Error, e );
            throw new LoaderException();
        }
    }

    public IRequest getLastRequest() {
        return this.lastResponse.get();
    }

    protected IResponseWriter createWriterInstance() {
        return new ResponseWriter( new HttpResponseRenderer() );
    }

    protected HttpRequestReader createLoaderInstance() {
        return new HttpRequestReader( new HttpRequestHydrator() );
    }

}

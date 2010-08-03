package com.vio.io.protocols.http.readers;

import com.vio.io.protocols.http.hydrators.IHttpRequestHydrator;
import com.vio.io.protocols.http.request.HttpRequest;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.core.readers.IRequestReader;
import com.vio.io.protocols.core.readers.ReaderException;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.vanilla.request.APIRequest;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:55:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestReader implements IRequestReader<BufferedInput, IHttpRequest> {
    private static final Logger log = Logger.getLogger( HttpRequestReader.class );
    private IHttpRequestHydrator hydrator;

    public HttpRequestReader( IHttpRequestHydrator hydrator ) {
        this.hydrator = hydrator;
    }

    public IHttpRequestHydrator getHydrator() {
        return this.hydrator;
    }

    public IHttpRequest readRequest( BufferedInput source ) throws ReaderException {
        try {
            String data = source.readLine();
            if ( data != null && !data.isEmpty() ) {
                return HttpRequest.buildRequest( data, this.getHydrator() );
            }

            return null;
        } catch (Throwable e ) {
            log.info( e.getMessage(), e );
            throw new ReaderException();
        }
    }
}

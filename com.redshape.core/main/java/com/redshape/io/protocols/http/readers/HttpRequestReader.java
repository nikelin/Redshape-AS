package com.redshape.io.protocols.http.readers;

import com.redshape.io.protocols.http.hydrators.IHttpRequestHydrator;
import com.redshape.io.protocols.http.request.HttpRequest;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
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

    @Override
    public IHttpRequest readRequest( BufferedInput source ) throws ReaderException {
        try {
            DataInputStream stream = new DataInputStream( source.getRawSource() );
            StringBuilder dataBuff = new StringBuilder();
            do {
                try {
                    dataBuff.append( stream.readUTF() );
                } catch ( EOFException e ) {
                    break;
                } catch ( IOException e ) {
                    log.error( e.getMessage(), e );
                    throw new ReaderException();
                }
            } while ( true );

            log.info("Incoming request: " + dataBuff.toString() );

            if ( dataBuff.length() != 0 ) {
                return HttpRequest.buildRequest( dataBuff.toString(), this.getHydrator() );
            }

            return null;
        } catch ( ReaderException e ) {
            throw e;
        } catch (Throwable e ) {
            log.info( e.getMessage(), e );
            throw new ReaderException();
        }
    }
}

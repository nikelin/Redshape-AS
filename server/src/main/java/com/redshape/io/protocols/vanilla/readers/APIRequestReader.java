package com.redshape.io.protocols.vanilla.readers;

import com.redshape.io.protocols.vanilla.request.ApiRequest;
import com.redshape.io.protocols.vanilla.hyndrators.IApiRequestHydrator;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import org.apache.log4j.Logger;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public class APIRequestReader implements IRequestReader<BufferedInput, IApiRequest> {
    private static final Logger log = Logger.getLogger( APIRequestReader.class );
    private IApiRequestHydrator hydrator;

    public APIRequestReader( IApiRequestHydrator hydrator ) {
        this.hydrator = hydrator;
    }

    public IApiRequestHydrator getHydrator() {
        return this.hydrator;
    }

    @Override
    public IApiRequest readRequest( BufferedInput source ) throws ReaderException {
        try {
            String data = source.readLine();
            if ( data != null && !data.isEmpty() ) {
                return ApiRequest.buildRequest( data, this.getHydrator() );
            }

            return null;
        } catch (Throwable e ) {
            log.info( e.getMessage(), e );
            throw new ReaderException();
        }
    }
}

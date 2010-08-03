package com.vio.io.protocols.vanilla.readers;

import com.vio.io.protocols.vanilla.request.APIRequest;
import com.vio.io.protocols.vanilla.hyndrators.ApiRequestHydrator;
import com.vio.io.protocols.readers.IRequestReader;
import com.vio.io.protocols.readers.ReaderException;
import com.vio.io.protocols.sources.input.BufferedInput;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import org.apache.log4j.Logger;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public class APIRequestReader implements IRequestReader<BufferedInput, IAPIRequest> {
    private static final Logger log = Logger.getLogger( APIRequestReader.class );
    private ApiRequestHydrator hydrator;

    public APIRequestReader( ApiRequestHydrator hydrator ) {
        this.hydrator = hydrator;
    }

    public ApiRequestHydrator getHydrator() {
        return this.hydrator;
    }

    public IAPIRequest readRequest( BufferedInput source ) throws ReaderException {
        try {
            String data = source.readLine();
            if ( data != null && !data.isEmpty() ) {
                return APIRequest.buildRequest( data, this.getHydrator() );
            }

            return null;
        } catch (Throwable e ) {
            log.info( e.getMessage(), e );
            throw new ReaderException();
        }
    }
}

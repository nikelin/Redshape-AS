package com.redshape.io.protocols.core.readers;

import com.redshape.io.net.request.IRequest;
import com.redshape.io.protocols.core.sources.input.InputSource;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public interface IRequestReader<V extends IRequest> extends IReader<V> {

    public V readRequest( T source) throws ReaderException;

}

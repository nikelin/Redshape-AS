package com.redshape.io.protocols.core.readers;

import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.sources.input.BufferedInput;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public interface IRequestReader<V extends IRequest> extends IReader<V> {

    public V readRequest( BufferedInput source) throws ReaderException;

}

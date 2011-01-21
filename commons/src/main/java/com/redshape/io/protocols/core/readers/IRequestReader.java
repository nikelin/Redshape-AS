package com.redshape.io.protocols.core.readers;

import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.sources.input.InputSource;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public interface IRequestReader<T extends InputSource, V extends IRequest> extends IReader<T, V> {

    public V readRequest( T source) throws ReaderException;

}

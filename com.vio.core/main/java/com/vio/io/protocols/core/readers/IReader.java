package com.vio.io.protocols.core.readers;

import com.vio.io.protocols.core.sources.input.InputSource;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public interface IReader<T extends InputSource, V> {

    public V readRequest( T source ) throws ReaderException;

}

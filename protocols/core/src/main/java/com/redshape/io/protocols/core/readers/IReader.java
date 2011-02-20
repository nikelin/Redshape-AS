package com.redshape.io.protocols.core.readers;

import java.io.InputStream;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 1, 2010
 */
public interface IReader<V> {

    public V readRequest( InputStream source ) throws ReaderException;

}

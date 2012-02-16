package com.redshape.utils.serializing;

import java.io.InputStream;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.utils
 * @date Mar 16, 2010
 */
public interface ObjectsLoader {

    public <T extends Object> T loadObject( T source, String path ) throws ObjectsLoaderException;

    public <T extends Object> T loadObject( T source, InputStream data ) throws ObjectsLoaderException;

    public Iterable<?> loadObjects( InputStream stream ) throws ObjectsLoaderException;

    public Iterable<?> loadObjects( String path ) throws ObjectsLoaderException;

}

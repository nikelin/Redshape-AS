package com.redshape.utils;

import java.io.File;
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

    public <T extends Object> T loadObject( T source, File data ) throws ObjectsLoaderException;

    public <T extends Object> T loadObject( T source, InputStream data ) throws ObjectsLoaderException;

    public Iterable loadObjects( String path ) throws ObjectsLoaderException;

    public Iterable loadObjects( File path ) throws ObjectsLoaderException;

}

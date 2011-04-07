package com.redshape.utils.serializing;

import java.io.OutputStream;
import java.util.Collection;

/**
 * @author root
 * @date 07/04/11
 * @package com.redshape.utils
 */
public interface ObjectsFlusher {

    public void flush( Object object, OutputStream target ) throws ObjectsLoaderException;

}

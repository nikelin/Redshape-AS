package com.redshape.io.protocols.core.sources.input;

import java.io.IOException;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.sources.input
 * @date Apr 1, 2010
 */
public interface InputStream extends InputSource {

    public int read() throws IOException;

}

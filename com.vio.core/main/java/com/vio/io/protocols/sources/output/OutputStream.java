package com.vio.io.protocols.sources.output;

import java.io.IOException;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.sources.output
 * @date Apr 1, 2010
 */
public interface OutputStream extends OutputSource {

    public void write( byte[] data ) throws IOException;

    public void write( char data ) throws IOException;

    public void flush() throws IOException;

    public void close() throws IOException;


}

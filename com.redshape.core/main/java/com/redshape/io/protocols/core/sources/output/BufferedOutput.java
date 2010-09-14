package com.redshape.io.protocols.core.sources.output;

import java.io.BufferedOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers.sources
 * @date Apr 1, 2010
 */
public class BufferedOutput implements OutputStream {
    private final static Logger log = Logger.getLogger( BufferedOutput.class );
    private BufferedOutputStream source;

    public BufferedOutput( java.io.OutputStream source) {
        this ( new BufferedOutputStream( source ) );
    }

    public BufferedOutput( BufferedOutputStream source ) {
        this.source = source;
    }

    public void write( byte[] data ) throws IOException {
        this.source.write(data);
    }

    public void write( char ch ) throws IOException {
        this.source.write(ch);
    }

    public void flush() throws IOException {
        this.source.flush();
    }

    public void close() throws IOException {
        this.source.close();
    }
    
}

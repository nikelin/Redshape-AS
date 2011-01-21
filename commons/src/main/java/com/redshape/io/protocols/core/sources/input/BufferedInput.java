package com.redshape.io.protocols.core.sources.input;

import java.io.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.sources.input
 * @date Apr 1, 2010
 */
public class BufferedInput implements InputStream {
    private BufferedReader reader;
    private java.io.InputStream source;

    public BufferedInput( java.io.InputStream source ) {
        this.source = source;
        this.reader = new BufferedReader( new InputStreamReader( source )  );
    }

    public String readLine() throws IOException {
        return this.reader.readLine();
    }

    @Override
    public int read() throws IOException {
        return this.reader.read();
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public java.io.InputStream getRawSource() {
        return this.source;
    }

}

package com.vio.io.protocols.sources.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.sources.input
 * @date Apr 1, 2010
 */
public class BufferedInput implements InputStream {
    private BufferedReader source;

    public BufferedInput( java.io.InputStream source ) {
        this.source = new BufferedReader( new InputStreamReader( source )  );
    }

    public String readLine() throws IOException {
        return this.source.readLine();
    }

    public int read() throws IOException {
        return this.source.read();
    }

    public void close() throws IOException {
        this.source.close();
    }

}

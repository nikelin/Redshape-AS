package com.vio.io.protocols.writers;

import com.vio.io.protocols.response.IResponse;
import com.vio.io.protocols.renderers.ResponseRenderer;
import com.vio.io.protocols.sources.output.OutputStream;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.render.RendererException;
import com.vio.utils.Constants;
import org.apache.log4j.Logger;

import java.util.Collection;


/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public class ResponseWriter implements IResponseWriter<OutputStream> {
    private static final Logger log = Logger.getLogger( ResponseWriter.class );
    private ResponseRenderer renderer;

    public ResponseWriter( ResponseRenderer renderer ) {
        this.renderer = renderer;
    }

    protected ResponseRenderer getRenderer() {
        return this.renderer;
    }

    public void writeResponse( OutputStream source, ExceptionWithCode exception ) throws WriterException {
        try {
            this.writeResponse( source, this.getRenderer().renderBytes(exception) );
        } catch( Throwable e ) {
            throw new WriterException();
        }
    }

    public void writeResponse( OutputStream source, Collection<? extends IResponse> response ) throws WriterException {
        try {
            this.writeResponse( source, this.getRenderer().renderBytes(response) );
        } catch ( RendererException e ) {
           throw new WriterException();
        }
    }

    public void writeResponse( OutputStream source, IResponse response ) throws WriterException {
        try {
            this.writeResponse(source, this.getRenderer().renderBytes(response) );
        } catch ( RendererException e ) {
            throw new WriterException();
        }
    }

    public void writeResponse( OutputStream source, byte[] bytes ) throws WriterException {
        try {
            source.write( bytes );
            source.write( Constants.EOL );
            source.flush();

            log.info("Written " + bytes.length + " bytes...");
        } catch ( Throwable e ) {
            throw new WriterException();
        }
    }

}

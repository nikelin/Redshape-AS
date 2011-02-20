package com.redshape.io.protocols.core.writers;

import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.renderers.IResponseRenderer;
import com.redshape.renderer.RendererException;
import com.redshape.utils.Constants;
import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public class ResponseWriter<T extends IResponse> implements IResponseWriter<T> {
    private static final Logger log = Logger.getLogger( ResponseWriter.class );
    private IResponseRenderer<String, T> renderer;

    public ResponseWriter( IResponseRenderer<String, T> renderer ) {
        this.renderer = renderer;
    }

    protected IResponseRenderer<String, T> getRenderer() {
        return this.renderer;
    }

    @Override
    public void writeResponse( OutputStream source, Throwable exception ) throws WriterException {
        try {
            this.writeResponse( source, this.getRenderer().renderBytes(exception) );
        } catch( Throwable e ) {
            throw new WriterException();
        }
    }

    @Override
    public void writeResponse( OutputStream source, Collection<T> response ) throws WriterException {
        try {
            this.writeResponse( source, this.getRenderer().renderBytes( response ) );
        } catch ( RendererException e ) {
           throw new WriterException();
        }
    }

    @Override
    public void writeResponse( OutputStream source, T response ) throws WriterException {
        try {
            this.writeResponse(source, this.getRenderer().renderBytes(response) );
        } catch ( RendererException e ) {
            throw new WriterException();
        }
    }

    @Override
    public void writeResponse( OutputStream source, byte[] bytes ) throws WriterException {
        try {
            source.write( bytes );
            source.write( (byte) Constants.EOL );
            source.flush();

            log.info("Written " + bytes.length + " bytes...");
        } catch ( Throwable e ) {
            throw new WriterException();
        }
    }

}

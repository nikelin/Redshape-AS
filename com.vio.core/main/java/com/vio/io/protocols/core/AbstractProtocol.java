package com.vio.io.protocols.core;

import com.vio.config.readers.ConfigReaderException;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.core.readers.IRequestReader;
import com.vio.io.protocols.core.readers.ReaderException;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.response.IResponse;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.core.sources.input.InputStream;
import com.vio.io.protocols.core.sources.output.OutputStream;
import com.vio.io.protocols.core.writers.ResponseWriter;
import com.vio.io.protocols.core.writers.WriterException;
import com.vio.utils.Registry;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 8:07:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProtocol<T extends IRequest, V extends IResponse> implements IProtocol<T, V> {
    private IRequestReader<BufferedInput, T> reader;
    private ResponseWriter writer;

    public void setReader( IRequestReader<BufferedInput, T> reader ) {
        this.reader = reader;
    }

    protected IRequestReader<BufferedInput, T> getReader() {
        return this.reader;
    }

    public void setWriter( ResponseWriter writer ) {
        this.writer = writer;
    }

    protected ResponseWriter getWriter() {
        return this.writer;
    }
    
    public void writeResponse( OutputStream stream, V response) throws WriterException {
        this.getWriter().writeResponse(stream, response);
    }

    public void writeResponse( OutputStream stream, Collection<? extends V> responses ) throws WriterException {
        this.getWriter().writeResponse(stream, responses);
    }

    public void writeResponse( OutputStream stream, ExceptionWithCode exception ) throws WriterException {
        this.getWriter().writeResponse(stream, exception);
    }

    public T readRequest( InputStream stream ) throws ReaderException {
        return this.readRequest( new BufferedInput( stream.getRawSource() ) );
    }

    public T readRequest( BufferedInput stream ) throws ReaderException {
        return this.getReader().readRequest(stream);
    }

    public boolean isAnonymousAllowed() throws ConfigReaderException {
        return Registry.getServerConfig().isAnonymousRequestsAllowed( this.getClass() );
    }

    public boolean isSupported( IProtocolVersion version ) {
        return this.getProtocolVersion().equals( version );
    }
}

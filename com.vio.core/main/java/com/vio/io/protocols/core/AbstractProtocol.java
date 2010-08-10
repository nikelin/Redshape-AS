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
import com.vio.io.protocols.core.writers.IResponseWriter;
import com.vio.io.protocols.core.writers.WriterException;
import com.vio.server.processors.request.IRequestsProcessor;
import com.vio.server.processors.connection.IClientsProcessor;
import com.vio.utils.Registry;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 8:07:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProtocol<T extends IRequest, V extends IResponse, I extends BufferedInput> implements IProtocol<T, V, I> {
    private IRequestReader<I, T> reader;
    private IResponseWriter writer;
    private IClientsProcessor clientsProcessor;
    private Class<? extends IRequestsProcessor<?, T>> requestsProcessor;

    @Override
    public void setReader( IRequestReader<I, T> reader ) {
        this.reader = reader;
    }

    @Override
    public IRequestReader<I, T> getReader() {
        return this.reader;
    }

    @Override
    public void setWriter( IResponseWriter writer ) {
        this.writer = writer;
    }

    @Override
    public IResponseWriter getWriter() {
        return this.writer;
    }

    @Override
    public void writeResponse( OutputStream stream, V response) throws WriterException {
        this.getWriter().writeResponse(stream, response);
    }

    @Override
    public void writeResponse( OutputStream stream, Collection<? extends V> responses ) throws WriterException {
        this.getWriter().writeResponse(stream, responses);
    }

    @Override
    public void writeResponse( OutputStream stream, ExceptionWithCode exception ) throws WriterException {
        this.getWriter().writeResponse(stream, exception);
    }

    public T readRequest( InputStream stream ) throws ReaderException {
        return this.readRequest( new BufferedInput( stream.getRawSource() ) );
    }

    @Override
    public T readRequest( I stream ) throws ReaderException {
        return this.getReader().readRequest(stream);
    }

    @Override
    public boolean isAnonymousAllowed() throws ConfigReaderException {
        return Registry.getApiServerConfig().isAnonymousRequestsAllowed( this.getClass() );
    }

    public boolean isSupported( IProtocolVersion version ) {
        return this.getProtocolVersion().equals( version );
    }

    @Override
    public void setRequestsProcessor( Class<? extends IRequestsProcessor<?, T>> processor ) {
        this.requestsProcessor = processor;
    }

    @Override
    public IRequestsProcessor<?, T> createRequestsProcessor() throws ProtocolException {
        try {
            return this.requestsProcessor.newInstance();
        } catch ( Throwable e ) {
            throw new ProtocolException();
        }
    }

    @Override
    public void setClientsProcessor( IClientsProcessor listener ) {
        this.clientsProcessor = listener;
    }

    @Override
    public IClientsProcessor getClientsProcessor() {
        return this.clientsProcessor;
    }
}

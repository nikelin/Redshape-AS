package com.redshape.io.protocols.core;

import com.redshape.utils.config.ConfigException;
import com.redshape.io.net.request.IRequest;
import com.redshape.io.net.request.RequestType;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.core.sources.output.OutputStream;
import com.redshape.io.protocols.core.writers.IResponseWriter;
import com.redshape.io.protocols.core.writers.WriterException;
import com.redshape.io.protocols.dispatchers.IDispatcher;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 4:11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocol<
                Z extends IRequest,
                T extends Z,
                Q extends IDispatcher,
                D extends Q,
                V extends IResponse> {

    public void setReader( IRequestReader<I, T> reader );

    public IRequestReader<I, T> getReader();

    public void setWriter( IResponseWriter writer );

    public IResponseWriter getWriter();

    public void setRequestsProcessor( Class<? extends IRequestsProcessor<?, Z>> processorClass );

    public IRequestsProcessor<?, Z> createRequestsProcessor( ISocketServer<?, V> server ) throws ProtocolException;

    public void setClientsProcessor( Class<? extends IClientsProcessor> processor );

    public IClientsProcessor createClientsProcessor(  ISocketServer<?, V> server  ) throws InstantiationException;

    public void writeResponse( OutputStream stream, V response) throws WriterException;

    public void writeResponse( OutputStream stream, Collection<? extends V> responses ) throws WriterException;

    public void writeResponse( OutputStream stream, Exception exception ) throws WriterException;

    public T readRequest( BufferedInput stream ) throws ReaderException;

    public boolean isAnonymousAllowed() throws ConfigException;

    public IProtocolVersion getProtocolVersion();

    public String getConstant( Constants id );

    public Q getRequestDispatcher( RequestType type );

    public void setRequestsDispatcher( RequestType type, Q dispatcher );

}

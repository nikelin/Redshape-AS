package com.redshape.io.protocols.core;

import com.redshape.config.ConfigException;
import com.redshape.config.IConfig;
import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.core.sources.input.InputStream;
import com.redshape.io.protocols.core.sources.output.OutputStream;
import com.redshape.io.protocols.core.writers.IResponseWriter;
import com.redshape.io.protocols.core.writers.WriterException;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.dispatchers.IDispatcher;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 8:07:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProtocol<
                                        Z extends IRequest,
                                        T extends Z,
                                        Q extends IDispatcher,
                                        D extends Q,
                                        V extends IResponse
                                    >
                    implements IProtocol<Z, T, Q, D, V> {
    private static final Logger log = Logger.getLogger( AbstractProtocol.class );

    private IRequestReader<I, T> reader;
    
    private IResponseWriter writer;
    
    @Autowired
    private Class<? extends IClientsProcessor> clientsProcessor;
    
    private Class<? extends IRequestsProcessor<?, Z>> requestsProcessor;
    
    private Map<RequestType, Q> dispatchers = new HashMap<RequestType, Q>();
    
    @Autowired( required = true )
    private IConfig config;
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }
    
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
    public boolean isAnonymousAllowed() throws ConfigException {
        for ( IConfig protocolNode : this.getConfig().get("sharedSettings").get("security").get("protocols").childs() ) {
            if( protocolNode.value().equals( this.getClass().getName() ) ) {
                return Boolean.valueOf( protocolNode.get("isAnonymousRequestsAllowed").value() );
            }
        }

        return false;
    }

    public boolean isSupported( IProtocolVersion version ) {
        return this.getProtocolVersion().equals( version );
    }

    @Override
    public void setRequestsProcessor( Class<? extends IRequestsProcessor<?, Z>> processor ) {
        this.requestsProcessor = processor;
    }

    @Override
    public IRequestsProcessor<?, Z> createRequestsProcessor( ISocketServer<?, V> server ) throws ProtocolException {
        try {
            return this.requestsProcessor.newInstance();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ProtocolException();
        }
    }

    @Override
    public void setClientsProcessor( Class<? extends IClientsProcessor> listener ) {
        this.clientsProcessor = listener;
    }

    protected Class<? extends IClientsProcessor> getClientsProcessor() {
    	return this.clientsProcessor;
    }
    
    @Override
    public IClientsProcessor createClientsProcessor( ISocketServer<?, V> server ) throws InstantiationException {
    	try {
    		IClientsProcessor processor = this.getClientsProcessor().newInstance();
    		processor.setContext( server );
    		
    		return processor;
    	} catch ( Throwable e ) {
    		throw new InstantiationException();
    	}
    }

    @Override
    public Q getRequestDispatcher( RequestType type ) {
        return this.dispatchers.get(type);
    }

    @Override
    public void setRequestsDispatcher( RequestType type, Q dispatcher ) {
        this.dispatchers.put( type, dispatcher );
    }
}

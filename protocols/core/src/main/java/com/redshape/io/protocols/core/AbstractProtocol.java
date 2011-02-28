package com.redshape.io.protocols.core;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.writers.IResponseWriter;
import com.redshape.io.protocols.core.writers.WriterException;
import com.redshape.io.protocols.dispatchers.IDispatcher;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.io.OutputStream;
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
										E extends IRequester,
                                        Z extends IRequest,
                                        T extends Z,
                                        Q extends IDispatcher<E, T, V>,
                                        D extends Q,
                                        V extends IResponse
                                    >
                    implements IProtocol<E, Z, T, Q, D, V> {
    private static final Logger log = Logger.getLogger( AbstractProtocol.class );

    private IRequestReader<T> reader;
    
    private IResponseWriter writer;
    
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
    public void setReader( IRequestReader<T> reader ) {
        this.reader = reader;
    }

    @Override
    public IRequestReader<T> getReader() {
        return this.reader;
    }

    @Override
    public void setWriter( IResponseWriter<V> writer ) {
        this.writer = writer;
    }

    @Override
    public IResponseWriter<V> getWriter() {
        return this.writer;
    }

    @Override
    public void writeResponse( OutputStream stream, V response) throws WriterException {
        this.getWriter().writeResponse(stream, response);
    }

    @Override
    public void writeResponse( OutputStream stream, Collection<V> responses ) throws WriterException {
        this.getWriter().writeResponse(stream, responses);
    }

    @Override
    public void writeResponse( OutputStream stream, Throwable exception ) throws WriterException {
        this.getWriter().writeResponse(stream, exception);
    }

    @Override
    public T readRequest( InputStream stream ) throws ReaderException {
        return this.readRequest( stream );
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
    public Q getRequestDispatcher( RequestType type ) {
        return this.dispatchers.get(type);
    }

    @Override
    public void setRequestsDispatcher( RequestType type, Q dispatcher ) {
        this.dispatchers.put( type, dispatcher );
    }
}

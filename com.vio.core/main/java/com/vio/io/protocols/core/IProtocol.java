package com.vio.io.protocols.core;

import com.vio.config.readers.ConfigReaderException;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.api.Constants;
import com.vio.io.protocols.core.readers.ReaderException;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.response.IResponse;
import com.vio.io.protocols.core.sources.input.InputStream;
import com.vio.io.protocols.core.sources.output.OutputStream;
import com.vio.io.protocols.core.writers.WriterException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 4:11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocol<T extends IRequest, V extends IResponse> {

    public void writeResponse( OutputStream stream, V response) throws WriterException;

    public void writeResponse( OutputStream stream, Collection<? extends V> responses ) throws WriterException;

    public void writeResponse( OutputStream stream, ExceptionWithCode exception ) throws WriterException;

    public T readRequest( InputStream stream ) throws ReaderException;

    public boolean isAnonymousAllowed() throws ConfigReaderException;

    public IProtocolVersion getProtocolVersion();

    public String getConstant( Constants id );

}

package com.redshape.io.protocols.core.hydrators;

import com.redshape.io.net.request.RequestException;
import com.redshape.io.net.request.RequestHeader;
import com.redshape.io.protocols.core.readers.ReaderException;

import java.util.Collection;

/**
 * @author nikelin
 */
public interface RequestHydrator {
    
    public void parse( String data ) throws RequestException;

    public Collection<RequestHeader> readHeaders() throws ReaderException;

}

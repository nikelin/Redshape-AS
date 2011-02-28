package com.redshape.io.protocols.core.hydrators;

import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.core.request.RequestException;
import com.redshape.io.protocols.core.request.RequestHeader;

import java.util.Collection;

/**
 * @author nikelin
 */
public interface RequestHydrator {
    
    public void parse( String data ) throws RequestException;

    public Collection<RequestHeader> readHeaders() throws ReaderException;

}

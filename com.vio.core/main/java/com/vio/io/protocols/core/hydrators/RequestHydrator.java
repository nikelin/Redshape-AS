package com.vio.io.protocols.core.hydrators;

import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.readers.ReaderException;
import com.vio.io.protocols.core.request.RequestException;
import com.vio.io.protocols.core.request.RequestHeader;

import java.util.Collection;
import java.util.List;

/**
 * @author nikelin
 */
public interface RequestHydrator<T extends IRequest> {
    
    public void parse( String data ) throws RequestException;

    public Collection<RequestHeader> readHeaders() throws ReaderException;

}

package com.vio.io.protocols.hydrators;

import com.vio.io.protocols.request.IRequest;
import com.vio.io.protocols.readers.ReaderException;
import com.vio.io.protocols.request.RequestException;
import com.vio.io.protocols.request.RequestHeader;

import java.util.List;

/**
 * @author nikelin
 */
public interface RequestHydrator<T extends IRequest> {
    
    public void parse( String data ) throws RequestException;

    public List<RequestHeader> readHeaders() throws ReaderException;

}

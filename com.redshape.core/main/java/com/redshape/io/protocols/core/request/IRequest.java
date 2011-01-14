package com.redshape.io.protocols.core.request;

import com.redshape.server.adapters.socket.client.ISocketAdapter;

import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io
 * @date Apr 18, 2010
 */
public interface IRequest {

    public Collection<RequestHeader> getHeaders();

    public RequestHeader getHeader( String name );

    public void setHeader( String name, Object value );

    public boolean hasHeader( String name );

    public void setHeaders( Collection<RequestHeader> headers );

    public void setSocket( ISocketAdapter socket );

    public ISocketAdapter getSocket();   

}

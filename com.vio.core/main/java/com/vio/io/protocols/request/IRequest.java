package com.vio.io.protocols.request;

import com.vio.server.adapters.socket.client.ISocketAdapter;

import java.util.List;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io
 * @date Apr 18, 2010
 */
public interface IRequest {

    public List<RequestHeader> getHeaders();

    public RequestHeader getHeader( String name );

    public void setHeader( String name, Object value );

    public boolean hasHeader( String name );

    public void setHeaders( List<RequestHeader> headers );

    public void setSocket( ISocketAdapter socket );

    public ISocketAdapter getSocket();
}

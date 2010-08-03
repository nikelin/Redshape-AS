package com.vio.io.protocols.vanilla.request;

import com.vio.io.protocols.request.IRequest;
import com.vio.persistence.entities.requesters.IRequester;

import java.util.Collection;
import java.util.List;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io
 * @date Apr 18, 2010
 */
public interface IAPIRequest extends IRequest {

    public void setInvokes( Collection<InterfaceInvocation> body );

    public void addInvoke( InterfaceInvocation invoke );

    public Collection<InterfaceInvocation> getInvokes();

    public IRequester getIdentity();

    public void setIdentity( IRequester identity );

    public boolean isAsync();
    
}

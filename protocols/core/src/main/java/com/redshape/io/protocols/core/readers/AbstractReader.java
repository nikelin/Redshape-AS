package com.redshape.io.protocols.core.readers;

import java.io.InputStream;

import com.redshape.io.net.request.IRequest;
import com.redshape.io.protocols.core.hydrators.RequestHydrator;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 18, 2010
 */
public abstract class AbstractReader<T extends InputStream, V extends IRequest> implements IRequestReader<V> {
    private RequestHydrator hydrator;

    public AbstractReader( RequestHydrator hydrator ) {
        this.hydrator = hydrator;
    }
    
    public RequestHydrator getHydrator() {
        return this.hydrator;
    }

}

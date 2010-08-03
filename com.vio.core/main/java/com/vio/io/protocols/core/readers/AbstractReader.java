package com.vio.io.protocols.core.readers;

import com.vio.io.protocols.core.hydrators.RequestHydrator;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.sources.input.InputSource;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.readers
 * @date Apr 18, 2010
 */
public abstract class AbstractReader<T extends InputSource, V extends IRequest> implements IRequestReader<T, V> {
    private RequestHydrator hydrator;

    public AbstractReader( RequestHydrator hydrator ) {
        this.hydrator = hydrator;
    }
    
    public RequestHydrator getHydrator() {
        return this.hydrator;
    }

}

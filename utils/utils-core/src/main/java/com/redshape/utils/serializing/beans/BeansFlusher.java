package com.redshape.utils.serializing.beans;

import com.redshape.utils.serializing.ObjectsFlusher;
import com.redshape.utils.serializing.ObjectsLoaderException;

import java.io.OutputStream;

/**
 * XStream based marshalling manager
 *
 * @author nikelin
 * @date 07/04/11
 * @package com.redshape.utils.serializing
 */
public class BeansFlusher extends AbstractBeansSerializer implements ObjectsFlusher {

    @Override
    public void flush(Object object, OutputStream target) throws ObjectsLoaderException {
       this.getLoader().toXML( object, target );
    }

}

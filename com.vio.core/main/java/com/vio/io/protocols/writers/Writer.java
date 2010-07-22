package com.vio.io.protocols.writers;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public interface Writer<T> {

    public void writeResponse( T source, byte[] bytes ) throws WriterException;

}

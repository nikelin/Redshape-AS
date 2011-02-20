package com.redshape.io.protocols.core.writers;

import java.io.OutputStream;


/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public interface IWriter {

    public void writeResponse( OutputStream source, byte[] bytes ) throws WriterException;

}

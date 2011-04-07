package com.redshape.utils.serializing.binary;

import com.redshape.utils.serializing.ObjectsFlusher;
import com.redshape.utils.serializing.ObjectsLoaderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author root
 * @date 07/04/11
 * @package com.redshape.utils.serializing.binary
 */
public class BinaryFlusher implements ObjectsFlusher {

	@Override
	public void flush(Object object, OutputStream target) throws ObjectsLoaderException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(10000);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject( object );
			out.flush();
			out.close();

			target.write( bos.toByteArray() );
		} catch ( IOException e ) {
			throw new ObjectsLoaderException( e.getMessage(), e );
		}
	}

}

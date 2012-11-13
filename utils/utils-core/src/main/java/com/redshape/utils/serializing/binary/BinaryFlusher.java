/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

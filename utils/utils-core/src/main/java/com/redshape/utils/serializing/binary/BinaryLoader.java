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

import com.redshape.utils.serializing.ObjectsLoader;
import com.redshape.utils.serializing.ObjectsLoaderException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author root
 * @date 07/04/11
 * @package com.redshape.utils.serializing.binary
 */
public class BinaryLoader implements ObjectsLoader {
	private static final Logger log = Logger.getLogger( ObjectsLoader.class );

    @Override
    public <T extends Object> T loadObject(T source, String path) throws ObjectsLoaderException {
        return this.loadObject( source, new File(path) );
    }
    
	public <T extends Object> T loadObject(T source, File data) throws ObjectsLoaderException {
		try {
			return this.loadObject( source, new FileInputStream(data) );
		} catch ( IOException e ) {
			throw new ObjectsLoaderException( e.getMessage(), e );
		}
	}

	@Override
	public <T extends Object> T loadObject(T source, InputStream data) throws ObjectsLoaderException {
		try {
			ObjectInputStream in = new ObjectInputStream(data);
			T result = (T) in.readObject();
			in.close();

			return result;
		} catch ( Throwable e ) {
			throw new ObjectsLoaderException( e.getMessage(), e );
		}
	}

	@Override
	public Iterable<?> loadObjects(InputStream stream) throws ObjectsLoaderException {
		throw new RuntimeException("Operation not supports");
	}

	@Override
	public Iterable<?> loadObjects(String path) throws ObjectsLoaderException {
		throw new RuntimeException("Operation not supports");
	}

	public Iterable<?> loadObjects(File path) throws ObjectsLoaderException {
		throw new RuntimeException("Operation not supports");
	}
}

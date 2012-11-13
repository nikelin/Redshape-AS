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

package com.redshape.utils.serializing.beans;

import com.redshape.utils.serializing.ObjectsLoader;
import com.redshape.utils.serializing.ObjectsLoaderException;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * XStream-based beans unmarshaller
 *
 * @author nikelin
 * @project vio
 * @package com.vio.beans
 * @date Mar 16, 2010
 */
public class BeansLoader extends AbstractBeansSerializer implements ObjectsLoader {
    private static final Logger log = Logger.getLogger( BeansLoader.class );

    @Override
    public <T extends Object> T loadObject( T source, String path ) throws ObjectsLoaderException {
        try {
            return this.loadObject( source, this.getResourcesLoader().loadFile(path) );
        } catch ( IOException e ) {
            throw new ObjectsLoaderException( e.getMessage(), e );
        }
    }
    
    @SuppressWarnings("unchecked")
	public <T extends Object> T loadObject( T object, File source ) throws ObjectsLoaderException {
        try {
            return (T) this.getLoader().fromXML( new FileInputStream( source ) );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ObjectsLoaderException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
	public <T extends Object> T loadObject( T object, InputStream source ) throws ObjectsLoaderException {
        try {
            return (T) this.getLoader().fromXML(source);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ObjectsLoaderException();
        }
    }

    @Override
	public Iterable<?> loadObjects( InputStream stream ) throws ObjectsLoaderException {
        try {
            return (Iterable<?>) this.getLoader().fromXML( stream );
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ObjectsLoaderException();
        }
	}

    @Override
    public Iterable<?> loadObjects( String path ) throws ObjectsLoaderException {
        try {
            return this.loadObjects( this.getResourcesLoader().loadFile( path ) );
        } catch ( IOException e ) {
            throw new ObjectsLoaderException();
        }
    }

    public Iterable<?> loadObjects( File file ) throws ObjectsLoaderException {
        try {
            XStream loader = this.getLoader();
            
            return (Iterable<?>) loader.fromXML( new FileReader(file) );
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ObjectsLoaderException();
        }
    }


}

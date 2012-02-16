package com.redshape.utils.serializing.beans;

import com.redshape.utils.serializing.ObjectsLoader;
import com.redshape.utils.serializing.ObjectsLoaderException;
import com.thoughtworks.xstream.XStream;

import java.io.*;

import org.apache.log4j.Logger;

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

    @SuppressWarnings("unchecked")
	public <T extends Object> T loadObject( T object, File source ) throws ObjectsLoaderException {
        try {
            return (T) this.getLoader().fromXML( new FileInputStream( source ) );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ObjectsLoaderException();
        }
    }

    @SuppressWarnings("unchecked")
	public <T extends Object> T loadObject( T object, InputStream source ) throws ObjectsLoaderException {
        try {
            return (T) this.getLoader().fromXML(source);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ObjectsLoaderException();
        }
    }

	public Iterable<?> loadObjects( InputStream stream ) throws ObjectsLoaderException {
        try {
            return (Iterable<?>) this.getLoader().fromXML( stream );
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ObjectsLoaderException();
        }
	}

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

package com.redshape.utils;

import com.thoughtworks.xstream.io.xml.DomReader;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.redshape.utils.helpers.XMLHelper;
import org.w3c.dom.Document;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.beans
 * @date Mar 16, 2010
 */
public class BeansLoader implements ObjectsLoader {
    private static final Logger log = Logger.getLogger( BeansLoader.class );
    private XStream loader;


    public <T extends Object> T loadObject( T object, File source ) throws ObjectsLoaderException {
        log.info( source.getPath() );
        try {
            return (T) this.getLoader().unmarshal( new DomReader( this.buildDocument( source ) ), object );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ObjectsLoaderException();
        }
    }

    public <T extends Object> T loadObject( T object, InputStream source ) throws ObjectsLoaderException {
        try {
            return (T) this.getLoader().unmarshal( new DomReader( this.buildDocument( source ) ), object );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ObjectsLoaderException();
        }
    }

    public Iterable loadObjects( String path ) throws ObjectsLoaderException {
        try {
            return this.loadObjects( Registry.getResourcesLoader().loadFile( path ) );
        } catch ( IOException e ) {
            throw new ObjectsLoaderException();
        }
    }

    public Iterable loadObjects( File file ) throws ObjectsLoaderException {
        try {
            XStream loader = this.getLoader();
            
            return (Iterable) loader.fromXML( new FileReader(file) );
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ObjectsLoaderException();
        }
    }

    protected XStream getLoader() {
        return this.getLoader( new PureJavaReflectionProvider(), new StaxDriver() );
    }

    protected XStream getLoader( ReflectionProvider provider, HierarchicalStreamDriver driver ) {
        if ( this.loader == null ) {
            this.loader = new XStream( provider, driver );
        }

        return this.loader;
    }

    private Document buildDocument( InputStream stream ) throws Throwable {
        return XMLHelper.buildDocument(stream);
    }

    private Document buildDocument( File file ) throws Throwable {
        return XMLHelper.buildDocument(file);
    }
}

package com.redshape.plugins.info;

import com.redshape.utils.BeansLoader;
import com.redshape.plugins.PluginInfo;
import com.redshape.plugins.loaders.PluginLoaderException;
import com.redshape.utils.ObjectsLoader;
import com.redshape.utils.ObjectsLoaderException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;

import org.apache.log4j.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLInfoLoader implements InfoLoader {
    private static final Logger log = Logger.getLogger( XMLInfoLoader.class );
    private InfoFile file;
    private ObjectsLoader beansProcessor;
    private static final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

    public XMLInfoLoader( InfoFile file ) {
        this( file, new BeansLoader() );
    }

    public XMLInfoLoader( InfoFile file, ObjectsLoader binder ) {
        this.file = file;
        this.beansProcessor = binder;
    }

    public InfoFile getFile() {
        return this.file;
    }

    public PluginInfo getInfo() throws PluginLoaderException {
        try {
            return this.loadInfo( new PluginInfo() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new PluginLoaderException( e.getMessage() );
        }
    }

    protected PluginInfo loadInfo( PluginInfo object ) throws ObjectsLoaderException {
        if ( this.getFile().isFileSource() ) {
            return this.getLoader().loadObject( object, (File) this.getFile().getSource() );
        } else {
            return this.getLoader().loadObject( object, (InputStream) this.getFile().getSource() );
        }
    }

    protected ObjectsLoader getLoader() {
        return this.beansProcessor;
    }

    protected DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return builderFactory.newDocumentBuilder();
    }

}

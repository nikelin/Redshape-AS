package com.redshape.plugins.info.loaders;

import com.redshape.plugins.info.PluginInfo;
import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.info.impl.InfoFile;
import com.redshape.plugins.loaders.PluginLoaderException;
import com.redshape.utils.serializing.ObjectsLoader;
import com.redshape.utils.serializing.ObjectsLoaderException;
import com.redshape.utils.serializing.beans.BeansLoader;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLInfoLoader implements IInfoLoader {
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

    public IPluginInfo getInfo() throws PluginLoaderException {
        try {
            return this.loadInfo( new PluginInfo() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new PluginLoaderException( e.getMessage() );
        }
    }

    protected IPluginInfo loadInfo( IPluginInfo object ) throws ObjectsLoaderException {
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

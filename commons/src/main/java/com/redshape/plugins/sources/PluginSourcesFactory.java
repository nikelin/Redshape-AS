package com.redshape.plugins.sources;

import com.redshape.plugins.loaders.PluginLoaderException;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:55:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginSourcesFactory {
    private final static Logger log = Logger.getLogger( PluginSourcesFactory.class );
    private final static PluginSourcesFactory instance = new PluginSourcesFactory();
    private static Map<String, Class<? extends PluginSource> > archiveTypes = new HashMap<String, Class<? extends PluginSource> >();

    private PluginSourcesFactory() {}

    public static PluginSourcesFactory getInstance() {
        return instance;
    }

    public PluginSource createSource( String path ) throws PluginLoaderException{
        return this.createSource( new File(path) );
    }

    public PluginSource createSource( File file ) throws PluginLoaderException {
        log.info("Creating plugin source on: " + file.getPath() );
        
        if ( file.isDirectory() ) {
            log.info("Unpacked plugin...");
            return this.createDirectorySource( file );
        } else {
            log.info("Archived plugin...");
            return this.createArchiveSource( file );
        }
    }

    public PluginSource createArchiveSource( File file ) throws PluginLoaderException {
        try {
            String[] fileNameParts = file.getName().split(".");
            String extension = fileNameParts[ fileNameParts.length - 1];

            Class<? extends PluginSource> clazz = this.archiveTypes.get(extension);
            if ( clazz == null ) {
                throw new PluginLoaderException("Unrecognized plugin archive type.");
            }

            Constructor<? extends PluginSource> constructor = clazz.getConstructor( file.getClass() );
            if ( constructor == null ) {
                throw new PluginLoaderException("Wrong plugin archive reader class.");
            }

            return constructor.newInstance(file);
        } catch( PluginLoaderException e ) {
            throw e;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new PluginLoaderException();   
        }
    }

    public PluginSourcesFactory addArchiveSourceType( String extension, Class<? extends PluginSource> sourceClass ) {
        this.archiveTypes.put( extension, sourceClass );
        return this;
    }

    public PluginSource createDirectorySource( File file ) {
        return new DirectoryPluginSource(file);
    }
}

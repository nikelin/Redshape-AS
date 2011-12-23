package com.redshape.plugins.loaders;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.loaders.compression.ICompressionMethodsRegistry;
import com.redshape.plugins.loaders.compression.ICompressionSupport;
import com.redshape.plugins.loaders.resources.CompressedPlugin;
import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.loaders.resources.PluginResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOError;
import java.io.IOException;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardLoaderFacade implements ILoadersFacade {

    @Autowired( required = true )
    private IPluginLoadersRegistry registry;

    private ICompressionMethodsRegistry compressionMethodsRegistry;

    public ICompressionMethodsRegistry getCompressionMethodsRegistry() {
        return compressionMethodsRegistry;
    }

    public void setCompressionMethodsRegistry(ICompressionMethodsRegistry compressionMethodsRegistry) {
        this.compressionMethodsRegistry = compressionMethodsRegistry;
    }

    public IPluginLoadersRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(IPluginLoadersRegistry registry) {
        this.registry = registry;
    }

    @Override
    public IPluginResource load(URI path) throws LoaderException {
        IPluginsLoader loader = this.getRegistry().selectLoader(path);
        if ( loader == null ) {
            throw new LoaderException("No loader bound on requested scheme!");
        }

        IPluginResource resource = loader.load(path);

        ICompressionSupport support = this.getCompressionMethodsRegistry().select(resource);
        if ( support != null ) {
            resource = this.applyCompressors(support, resource);
        }
        
        return resource;
    }
    
    protected IPluginResource applyCompressors( ICompressionSupport support, IPluginResource resource ) throws LoaderException {
        try {
            do {
                return new CompressedPlugin( resource,
                    support.decompressStream(resource.getInputStream()),
                    support.compressStream(resource.getOuputStream()) );
            } while ( this.getCompressionMethodsRegistry().select(resource) != null );
        } catch ( IOException e) { 
            throw new LoaderException( e.getMessage(), e );
        }
    }
}

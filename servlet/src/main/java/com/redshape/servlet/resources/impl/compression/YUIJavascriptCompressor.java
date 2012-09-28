package com.redshape.servlet.resources.impl.compression;

import com.redshape.servlet.resources.IWebResourcesCompressor;
import com.redshape.servlet.resources.types.Resource;
import com.redshape.utils.Commons;
import com.redshape.utils.IResourcesLoader;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/29/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class YUIJavascriptCompressor implements IWebResourcesCompressor {

    private int linebreak;
    private boolean munge;
    private boolean verbose;
    private boolean preserveAllSemiColons;
    private boolean disableOptimizations;
    private IResourcesLoader loader;
    private String resourcesRoot;

    public YUIJavascriptCompressor( IResourcesLoader loader, String resourcesRoot ) {
        Commons.checkNotNull(loader);
        Commons.checkNotNull(resourcesRoot);

        this.loader = loader;
        this.resourcesRoot = resourcesRoot;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public int getLinebreak() {
        return linebreak;
    }

    public void setLinebreak(int linebreak) {
        this.linebreak = linebreak;
    }

    public boolean isMunge() {
        return munge;
    }

    public void setMunge(boolean munge) {
        this.munge = munge;
    }

    public boolean isPreserveAllSemiColons() {
        return preserveAllSemiColons;
    }

    public void setPreserveAllSemiColons(boolean preserveAllSemiColons) {
        this.preserveAllSemiColons = preserveAllSemiColons;
    }

    public boolean isDisableOptimizations() {
        return disableOptimizations;
    }

    public void setDisableOptimizations(boolean disableOptimizations) {
        this.disableOptimizations = disableOptimizations;
    }

    protected IResourcesLoader getLoader() {
        return loader;
    }

    protected String getResourcesRoot() {
        return this.resourcesRoot;
    }

    protected InputStreamReader load( String path ) throws IOException {
        InputStream resource = this.getLoader().loadResource( this.getResourcesRoot() + File.separator + path);
        if ( resource == null ) {
            throw new IOException("Resource not exists");
        }

        return new InputStreamReader(resource);
    }

    @Override
    public boolean isSupported(Resource resource) {
        return resource.getHref().endsWith("js")
                && !resource.getHref().contains("://");
    }

    @Override
    public String compress(List<String> paths) throws IOException {
        StringBuilder builder = new StringBuilder();
        for ( String path : paths ) {
            builder.append( this.compress(path) );
        }

        return builder.toString();
    }

    @Override
    public String compress(String path) throws IOException {
        JavaScriptCompressor compressor = new JavaScriptCompressor(this.load(path), null);
        StringWriter writer = new StringWriter();
        compressor.compress( writer, this.getLinebreak(), this.isMunge(),
                this.isVerbose(),
                this.isPreserveAllSemiColons(),
                this.isDisableOptimizations() );

        return writer.toString();
    }
}

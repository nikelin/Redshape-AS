package com.redshape.servlet.resources.impl;

import com.redshape.servlet.core.Constants;
import com.redshape.servlet.resources.IWebResourceWriter;
import com.redshape.servlet.resources.IWebResourcesCompressor;
import com.redshape.servlet.resources.types.Link;
import com.redshape.servlet.resources.types.Script;
import com.redshape.servlet.resources.types.Style;
import com.redshape.servlet.views.ViewHelper;
import com.redshape.utils.IHasher;
import com.redshape.utils.IResourcesLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/29/12
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompressionWebResourcesWriter implements IWebResourceWriter {

    private long cacheLifetime = com.redshape.utils.Constants.TIME_HOUR * 5;
    private String outputPath;
    private String externalPath;
    private IHasher hasher;
    private IResourcesLoader loader;
    private IWebResourcesCompressor javaScriptCompressor;
    private IWebResourcesCompressor cssCompressor;

    public CompressionWebResourcesWriter(String outputPath,
                                         String externalPath,
                                         IHasher hasher,
                                         IResourcesLoader loader,
                                         IWebResourcesCompressor javaScriptCompressor,
                                         IWebResourcesCompressor cssCompressor) {
        this.externalPath = externalPath;
        this.loader = loader;
        try {
            this.outputPath = this.initOutput(outputPath);
        } catch ( IOException e ) {
            throw new IllegalArgumentException("<output>", e );
        }
        this.hasher = hasher;
        this.javaScriptCompressor = javaScriptCompressor;
        this.cssCompressor = cssCompressor;
    }

    public void setCacheLifetime(long cacheLifetime) {
        this.cacheLifetime = cacheLifetime;
    }

    public long getCacheLifetime() {
        return cacheLifetime;
    }

    public void setCacheLifetime(int cacheLifetime) {
        this.cacheLifetime = cacheLifetime;
    }

    protected String initOutput( String outputPath ) throws IOException {
        File file = this.getLoader().loadFile(outputPath);
        if ( file == null ) {
            throw new IOException("<output> not exists");
        }

        return file.getAbsolutePath();
    }

    protected String getExternalPath() {
        return this.externalPath;
    }

    protected IResourcesLoader getLoader() {
        return this.loader;
    }

    protected IHasher getHasher() {
        return this.hasher;
    }

    protected String getOutputPath() {
        return this.outputPath;
    }

    protected IWebResourcesCompressor getJavaScriptCompressor() {
        return this.javaScriptCompressor;
    }

    protected IWebResourcesCompressor getCSSCompressor() {
        return this.cssCompressor;
    }

    protected String printStyle( Style style) {
        StringBuilder builder = new StringBuilder();
        builder.append("<link rel=\"stylesheet\" type=\"")
               .append(style.getType())
               .append("\" ")
               .append("href=\"")
               .append( ViewHelper.url( style.getHref().contains("://") ? style.getHref() : this.getExternalPath() + "/" + style.getHref() ) )
               .append("\"/>");

        return builder.toString();
    }

    protected String printScript( Script script ) {
        StringBuilder builder = new StringBuilder();
        builder.append("<script type=\"")
               .append(script.getType())
               .append("\" ")
               .append("src=\"")
               .append( ViewHelper.url( script.getHref().contains("://") ? script.getHref() : this.getExternalPath() + "/" + script.getHref() ) )
               .append("\"></script>\n");

        return builder.toString();
    }

    protected boolean hasCache( String path ) throws IOException {
        File file = this.getLoader().loadFile( this.getOutputPath() + File.separator + path );
        if ( file == null ) {
            return false;
        }

        if ( new Date().getTime() > file.lastModified() + this.getCacheLifetime() ) {
            return false;
        }

        return true;
    }

    protected void saveCache( String path, String data ) throws IOException {
        File file = new File(this.getOutputPath() + File.separator + path );
        if ( file.exists() ) {
            file.delete();
        }


        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        writer.write(data);
        writer.close();
    }

    @Override
    public String writeScripts(List<Script> scripts) {
        try {
            StringBuilder output = new StringBuilder();
            StringBuilder composedPath = new StringBuilder();
            List<String> paths = new ArrayList<String>();
            for ( Script script : scripts ) {
                if ( !this.getJavaScriptCompressor().isSupported(script) ) {
                    output.append( this.printScript(script) );
                    continue;
                }

                paths.add( script.getHref() );
                composedPath.append(script.getHref());
            }

            String cacheKey = this.hasher.hash(composedPath.toString()) + ".js";
            if ( !this.hasCache( cacheKey ) ) {
                this.saveCache(cacheKey, this.getJavaScriptCompressor().compress(paths) );
            }

            output.append( this.printScript( new Script("text/javascript", cacheKey) ) );

            return output.toString();
        } catch ( IOException e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    @Override
    public String writeStyles(List<Style> styles) {
        try {
            StringBuilder output = new StringBuilder();
            StringBuilder composedPath = new StringBuilder();
            List<String> paths = new ArrayList<String>();
            for ( Style style : styles ) {
                if ( !this.getCSSCompressor().isSupported(style) ) {
                    output.append( this.printStyle(style) );
                    continue;
                }

                paths.add( style.getHref() );
                composedPath.append(style.getHref());
            }

            String cacheKey = this.hasher.hash(composedPath.toString()) + ".css";
            if ( !this.hasCache( cacheKey ) ) {
                this.saveCache(cacheKey, this.getCSSCompressor().compress(paths) );
            }

            output.append( this.printStyle(new Style("text/css", cacheKey, Style.DEFAULT_MEDIA)) );

            return output.toString();
        } catch ( IOException e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    @Override
    public String writeLinks(List<Link> links) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

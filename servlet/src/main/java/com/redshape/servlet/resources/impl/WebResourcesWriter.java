package com.redshape.servlet.resources.impl;

import com.redshape.servlet.resources.IWebResourceWriter;
import com.redshape.servlet.resources.IWebResourcesCompressor;
import com.redshape.servlet.resources.types.Link;
import com.redshape.servlet.resources.types.Script;
import com.redshape.servlet.resources.types.Style;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.yahoo.platform.yui.compressor.YUICompressor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class WebResourcesWriter implements IWebResourceWriter {
    /**
     * This option turns on JS / CSS files compression
     */
    private boolean enabledCompression;

    private IWebResourcesCompressor javaScriptCompressor;
    private IWebResourcesCompressor cssCompressor;

    public void setEnabledCompression( boolean value ) {
        this.enabledCompression = value;
    }

    @Override
    public boolean isEnabledCompression() {
        return this.enabledCompression;
    }

    @Override
    public void setJavaScriptCompressor(IWebResourcesCompressor compressor) {
        this.javaScriptCompressor = compressor;
    }

    @Override
    public void setCSSCompressor(IWebResourcesCompressor compressor) {
        this.cssCompressor = compressor;
    }

    @Override
    public String writeScripts(List<Script> scripts) {
        return null;
    }

    @Override
    public String writeStyles(List<Style> styles) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String writeLinks(List<Link> links) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

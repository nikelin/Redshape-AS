package com.redshape.servlet.resources;

import com.redshape.servlet.resources.types.Link;
import com.redshape.servlet.resources.types.Script;
import com.redshape.servlet.resources.types.Style;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public interface IWebResourceWriter {

    public boolean isEnabledCompression();

    public void setJavaScriptCompressor( IWebResourcesCompressor compressor );

    public void setCSSCompressor( IWebResourcesCompressor compressor );

    public String writeScripts( List<Script> scripts );

    public String writeStyles( List<Style> styles );

    public String writeLinks( List<Link> links );

}